CREATE TABLE ek_event_type(
   id INT,
   name VARCHAR(64)  NOT NULL DEFAULT '',
   PRIMARY KEY(id)
);

CREATE TABLE ek_contribution_type(
   id INT,
   name VARCHAR(64)  NOT NULL DEFAULT '',
   PRIMARY KEY(id)
);

CREATE TABLE ek_team(
   uuid VARCHAR(50)  DEFAULT uuid(),
   name VARCHAR(64)  NOT NULL,
   main_color VARCHAR(6) ,
   secondary_color VARCHAR(6) ,
   total_score BIGINT NOT NULL,
   PRIMARY KEY(uuid)
);

CREATE TABLE ek_team_role(
   id INT,
   name VARCHAR(64)  NOT NULL DEFAULT '',
   luckperms_role VARCHAR(256)  NOT NULL DEFAULT '',
   PRIMARY KEY(id)
);

CREATE TABLE _ek_player_(
   uuid VARCHAR(50) ,
   last_playername VARCHAR(64)  NOT NULL DEFAULT '',
   last_login DATETIME,
   total_playtime BIGINT NOT NULL,
   last_month_playtime BIGINT NOT NULL,
   updated_at DATETIME,
   created_at DATETIME NOT NULL DEFAULT current_timestamp(),
   team_role_id INT,
   team_uuid VARCHAR(50) ,
   PRIMARY KEY(uuid),
   FOREIGN KEY(team_role_id) REFERENCES ek_team_role(id),
   FOREIGN KEY(team_uuid) REFERENCES ek_team(uuid)
);

CREATE TABLE ek_event_log(
   id INT AUTO_INCREMENT,
   date_event DATETIME NOT NULL DEFAULT current_timestamp(),
   data TEXT NOT NULL,
   uuid_team VARCHAR(50)  NOT NULL,
   uuid_player VARCHAR(50)  NOT NULL,
   event_type INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(uuid_team) REFERENCES ek_team(uuid),
   FOREIGN KEY(uuid_player) REFERENCES _ek_player_(uuid),
   FOREIGN KEY(event_type) REFERENCES ek_event_type(id)
);

CREATE TABLE ek_team_player_contribution(
   id INT,
   uuid_player VARCHAR(50)  NOT NULL,
   uuid_team VARCHAR(50)  NOT NULL,
   date_ DATETIME NOT NULL DEFAULT current_timestamp(),
   contribution BIGINT NOT NULL,
   id_event_log INT,
   id_contribution_type INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_event_log) REFERENCES ek_event_log(id),
   FOREIGN KEY(id_contribution_type) REFERENCES ek_contribution_type(id)
);
