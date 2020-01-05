SELECT DISTINCT session_id,game_id,date,matches FROM 
(
SELECT session_id, game_id,date,
COUNT(*) OVER (PARTITION BY session_id,game_id ORDER BY date) as matches
FROM game_plays
WHERE 1=1
AND game_type = 'Loto649' 
AND value IN (21,12,6,28,13,19)
ORDER BY session_id, game_id
) S
WHERE s.matches > 2
ORDER BY matches desc
