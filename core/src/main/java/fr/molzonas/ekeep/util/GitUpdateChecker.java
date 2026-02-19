package fr.molzonas.ekeep.util;

import fr.molzonas.ekeep.logging.EKLogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class GitUpdateChecker {
    private static final String OWNER = "Molzonas";
    private static final String REPOSITORY = "EkEep";

    public record Result(String current, String latest, boolean upToDate, String url) {}

    /**
     * Check the latest GitHub release by reading /releases/latest
     * @param currentVersion Current plugin.yml plugin version
     * @return Version verification result with URL to update
     */
    public CompletableFuture<Result> checkLatest(String currentVersion) {
        return checkLatest(currentVersion, "");
    }

    /**
     * Check the latest GitHub release by reading /releases/latest
     * @param currentVersion Current plugin.yml plugin version
     * @param token GitHub token to passthrough limitations
     * @return Version verification result with URL to update
     */
    public CompletableFuture<Result> checkLatest(String currentVersion, String token) {
        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();

        String url = "https://github.com/" + OWNER + "/" + REPOSITORY + "/releases/latest";
        HttpRequest.Builder req = HttpRequest.newBuilder(URI.create(url))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .header("User-Agent", "PaperPluginUpdateCheck/1.0 (+https://github.com/" + OWNER + "/" + REPOSITORY + ")");
        if (token != null && !token.isBlank()) {
            req.header("Authorization", "Bearer " + token.trim());
        }

        return client.sendAsync(req.build(), HttpResponse.BodyHandlers.discarding())
                .thenApply(resp -> {
                    String location = resp.headers().firstValue("location").orElse("");
                    String latestTag = location.substring(location.lastIndexOf('/') + 1);
                    latestTag = stripV(latestTag);
                    String current = stripV(currentVersion);
                    boolean upToDate = compareSemver(current, latestTag) >= 0;
                    return new Result(current, latestTag, upToDate,
                            "https://github.com/" + OWNER + "/" + REPOSITORY + "/releases/tag/" + latestTag);
                })
                .exceptionally(ex -> new Result(stripV(currentVersion), "unknown", true, url));
    }

    private String stripV(String v) {
        if (v == null) return "";
        v = v.trim();
        if (v.startsWith("v") || v.startsWith("V")) v = v.substring(1);
        return v;
    }

    private int compareSemver(String a, String b) {
        String[] pa = a.split("-", 2);
        String[] pb = b.split("-", 2);
        int[] na = parseNums(pa[0]);
        int[] nb = parseNums(pb[0]);
        for (int i = 0; i < 3; i++) {
            int d = Integer.compare(na[i], nb[i]);
            if (d != 0) return d;
        }
        boolean apre = pa.length > 1;
        boolean bpre = pb.length > 1;
        if (apre && !bpre) return -1;
        if (!apre && bpre) return 1;
        if (apre) return pa[1].compareToIgnoreCase(pb[1]);
        return 0;
    }

    private int[] parseNums(String core) {
        String[] s = core.split("\\.");
        int[] out = new int[]{0,0,0};
        for (int i = 0; i < Math.min(3, s.length); i++) {
            try { out[i] = Integer.parseInt(s[i]); } catch (NumberFormatException ignored) {}
        }
        return out;
    }

    /**
     * Check the latest GitHub release and log in console
     * @param plugin The currently used plugin
     */
    public void logCheckAsync(Plugin plugin) {
        logCheckAsync(plugin, "");
    }

    public void logCheckAsync(Plugin plugin, String tokenOpt) {
        String current = plugin.getPluginMeta().getVersion();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () ->
                checkLatest(current, tokenOpt).thenAccept(res -> {
                    if (res.latest().equals("unknown")) {
                        plugin.getLogger().info(EKLogger.LOG_PREFIX + "Unable to retrieve latest version (offline/GitHub unavailable).");
                        return;
                    }
                    if (res.upToDate()) {
                        plugin.getLogger().info(EKLogger.LOG_PREFIX + "Up to date (" + res.current() + ").");
                    } else {
                        plugin.getLogger().warning(EKLogger.LOG_PREFIX + "New version available: " + res.latest() +
                                " (current: " + res.current() + ") → " + res.url());
                    }
                })
        );
    }
}
