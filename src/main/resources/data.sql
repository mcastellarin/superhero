INSERT INTO superheroes.superheroes(created_by, created_date, name, secret_identity, name_secret_identity, origin) VALUES
  ('ADMIN', CURRENT_TIMESTAMP, 'SPIDER-MAN', 'CIVIL_IDENTITY', 'PERTER PARKER', 'OTHERS'),
  ('ADMIN', CURRENT_TIMESTAMP, 'WONDER WOMAN', 'CIVIL_IDENTITY', 'DIANA PRINCE', 'MYTHOLOGICAL_GODS'),
  ('ADMIN', CURRENT_TIMESTAMP, 'BAT-MAN', 'CIVIL_IDENTITY', 'BRUCE WAYNE', 'OTHERS'),
  ('ADMIN', CURRENT_TIMESTAMP, 'X-MEN', 'SUPERHERO_IDENTITY', '', 'MUTANT'),
  ('ADMIN', CURRENT_TIMESTAMP, 'SUPERMAN', 'CIVIL_IDENTITY', 'CLARK KENT', 'DEMIGODS');

INSERT INTO superheroes.superpowers(created_by, created_date, name) VALUES
  ('ADMIN', CURRENT_TIMESTAMP, 'TELEKINESIS'),
  ('ADMIN', CURRENT_TIMESTAMP, 'INVULNERABILITY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'LIGHT CREATION'),
  ('ADMIN', CURRENT_TIMESTAMP, 'TELEPATHY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'SUPER VELOCITY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'HIGHER SENSES'),
  ('ADMIN', CURRENT_TIMESTAMP, 'MAGICAL POWER'),
  ('ADMIN', CURRENT_TIMESTAMP, 'MENTAL POWER'),
  ('ADMIN', CURRENT_TIMESTAMP, 'ENERGY PROJECTION'),
  ('ADMIN', CURRENT_TIMESTAMP, 'SUPER HUMAN STRENGHT'),
  ('ADMIN', CURRENT_TIMESTAMP, 'INVISIBILITY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'FLY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'INMORTALITY'),
  ('ADMIN', CURRENT_TIMESTAMP, 'REFLEXES'),
  ('ADMIN', CURRENT_TIMESTAMP, 'BALANCE'),
  ('ADMIN', CURRENT_TIMESTAMP, 'ENDURANCE');
   
SET @spiderManSuperhero = 'SPIDER-MAN'; 
SET @WonderWomanSuperhero = 'WONDER WOMAN'; 
  
SET @SuperHumanStrenghtSuperpower = 'SUPER HUMAN STRENGHT';
 
INSERT INTO superheroes.superheroes_superpowers(superhero_Id, superpower_Id) VALUES
	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @spiderManSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = @SuperHumanStrenghtSuperpower),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @spiderManSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'BALANCE'),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @spiderManSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'ENDURANCE'),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @WonderWomanSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name =  @SuperHumanStrenghtSuperpower),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @WonderWomanSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'FLY'),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @WonderWomanSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'SUPER VELOCITY'),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @WonderWomanSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'INMORTALITY'),
 	(SELECT sh.id FROM superheroes.superheroes sh WHERE sh.name = @WonderWomanSuperhero, SELECT sp.id FROM superheroes.superpowers sp WHERE sp.name = 'REFLEXES');
 

  
  