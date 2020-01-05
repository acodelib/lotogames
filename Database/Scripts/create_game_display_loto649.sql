DROP VIEW IF EXISTS game_display_loto649;
CREATE VIEW game_display_loto649 AS
SELECT 
	gp.session_id,gp.date,gp.game_id, 
	max(CASE WHEN value_name='#No 1' THEN value ELSE NULL END) n1,
	max(CASE WHEN value_name='#No 2' THEN value ELSE NULL END) n2,
	max(CASE WHEN value_name='#No 3' THEN value ELSE NULL END) n3,
	max(CASE WHEN value_name='#No 4' THEN value ELSE NULL END) n4,
	max(CASE WHEN value_name='#No 5' THEN value ELSE NULL END) n5,
	max(CASE WHEN value_name='#No 6' THEN value ELSE NULL END) n6
FROM game_plays gp
WHERE 1 = 1 
AND gp.game_type = 'Loto649'
GROUP BY 1,2,3;