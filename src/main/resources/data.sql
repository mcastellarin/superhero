INSERT INTO superheroes.superheroes(created_by, created_date, name, secret_identity, name_secret_identity, origin) VALUES
  ('admin', CURRENT_TIMESTAMP, 'SPIDER-MAN', 'CIVIL_IDENTITY', 'PERTER PARKER', 'OTHERS'),
  ('admin', CURRENT_TIMESTAMP, 'WONDER WOMAN', 'CIVIL_IDENTITY', 'DIANA PRINCE', 'MYTHOLOGICAL_GODS'),
  ('admin', CURRENT_TIMESTAMP, 'BAT-MAN', 'CIVIL_IDENTITY', 'BRUCE WAYNE', 'OTHERS'),
  ('admin', CURRENT_TIMESTAMP, 'X-MEN', 'SUPERHERO_IDENTITY', '', 'MUTANT'),
  ('admin', CURRENT_TIMESTAMP, 'SUPERMAN', 'CIVIL_IDENTITY', 'CLARK KENT', 'DEMIGODS');

INSERT INTO superheroes.superpowers(created_by, created_date, name) VALUES
  ('admin', CURRENT_TIMESTAMP, 'TELEKINESIS'),
  ('admin', CURRENT_TIMESTAMP, 'INVULNERABILITY'),
  ('admin', CURRENT_TIMESTAMP, 'LIGHT CREATION'),
  ('admin', CURRENT_TIMESTAMP, 'TELEPATHY'),
  ('admin', CURRENT_TIMESTAMP, 'SUPER VELOCITY'),
  ('admin', CURRENT_TIMESTAMP, 'HIGHER SENSES'),
  ('admin', CURRENT_TIMESTAMP, 'MAGICAL POWER'),
  ('admin', CURRENT_TIMESTAMP, 'MENTAL POWER'),
  ('admin', CURRENT_TIMESTAMP, 'ENERGY PROJECTION'),
  ('admin', CURRENT_TIMESTAMP, 'SUPER HUMAN STRENGHT'),
  ('admin', CURRENT_TIMESTAMP, 'INVISIBILITY'),
  ('admin', CURRENT_TIMESTAMP, 'FLY'),
  ('admin', CURRENT_TIMESTAMP, 'INMORTALITY'),
  ('admin', CURRENT_TIMESTAMP, 'REFLEXES'),
  ('admin', CURRENT_TIMESTAMP, 'BALANCE'),
  ('admin', CURRENT_TIMESTAMP, 'ENDURANCE');
   
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
 

  
  