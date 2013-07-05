-- Les heures étaient toutes 00h00, pour que le passage au status déjà joué se passe bien
-- je les ai toutes passées à 13h. 
UPDATE 
    `workshopmanager`.`WORKSHOP_SESSION` 
SET 
    `next_play` = DATE_ADD(`next_play`, INTERVAL 13 HOUR)