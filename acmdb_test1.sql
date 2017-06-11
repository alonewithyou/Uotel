CREATE TABLE IF NOT EXISTS users (
  id       INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL UNIQUE,
  passwd   VARCHAR(20) NOT NULL,
  fullname VARCHAR(20),
  address  TEXT,
  phone    VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS ths (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  housename TEXT NOT NULL,
  address   TEXT,
  url       TEXT,
  tel       VARCHAR(20),
  yearbuilt INT NOT NULL,
  price     INT NOT NULL,
  ownerid     INT NOT NULL,
  FOREIGN KEY(ownerid) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS periods (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  starttime DATE,
  endtime   DATE,
  thid      INT NOT NULL,
  FOREIGN KEY(thid) REFERENCES ths(id)
);

CREATE TABLE reserves (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  userid    INT NOT NULL,
  thid      INT NOT NULL,
  periodid  INT NOT NULL,
  stayed    INT NOT NULL,
  FOREIGN KEY(userid) REFERENCES users(id),
  FOREIGN KEY(thid) REFERENCES ths(id),
  FOREIGN KEY(periodid) REFERENCES periods(id)
);

CREATE TABLE IF NOT EXISTS favors (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  userid    INT NOT NULL,
  thid      INT NOT NULL,
  FOREIGN KEY(userid) REFERENCES users(id),
  FOREIGN KEY(thid) REFERENCES ths(id)
);

CREATE TABLE IF NOT EXISTS keywords (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  thid      INT NOT NULL,
  content   TEXT,
  FOREIGN KEY(thid) REFERENCES ths(id)
);

CREATE TABLE IF NOT EXISTS feedbacks (
  id        INT PRIMARY KEY AUTO_INCREMENT,
  content   TEXT,
  score     INT NOT NULL,
  thid      INT NOT NULL,
  userid    INT NOT NULL,
  FOREIGN KEY(userid) REFERENCES users(id),
  FOREIGN KEY(thid) REFERENCES ths(id)
);