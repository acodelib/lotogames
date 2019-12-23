DROP TABLE IF EXISTS game_plays;
CREATE TABLE game_plays (
        session_id integer PRIMARY KEY autoincrement,
        date text,
        game integer,        
        value_name text,
        value int        
);
