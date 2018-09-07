insert into permissao (id, nome) values 
	(1, 'USUARIO')
;
	
insert into usuario (id, nome, login, senha, ativo) values 
	(1, 'Adri', 'adri', '$2a$10$JvyF9q/k/eYwXTVjc4Ay0OT/dCwjW14eT88q3e587jaENTvtt30s2', true)
;
	
insert into usuario_permissao (usuario_id, permissao_id) values 
	(1, 1)
;