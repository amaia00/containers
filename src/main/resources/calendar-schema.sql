CREATE TABLE agenda(
  nom VARCHAR(255) PRIMARY KEY
);

CREATE TABLE event(
  title VARCHAR(255),
  start DATETIME,
  end DATETIME,
  description TEXT,
  id VARCHAR(255),
  calendar VARCHAR(255) REFERENCES calendar(nom),
  PRIMARY KEY (id,calendar)
);
