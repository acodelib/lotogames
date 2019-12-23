DROP TABLE IF EXISTS game_plays;
CREATE TABLE game_plays (
        session_id integer,
        date text,
        game_id integer,        
        game_type text,        
        value_name text,
        value int        
);
