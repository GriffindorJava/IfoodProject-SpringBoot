insert into tb_cozinha (nome) values ('tailandesa');
insert into tb_cozinha (nome) values ('indiana');

insert into tb_restaurante (nome , taxa_Frete , cozinha_id) values ('thai goumet' , 5.90 , 1 );
insert into tb_restaurante (nome , taxa_Frete , cozinha_id) values ('thai delivery' , 9.90 , 1 );
insert into tb_restaurante (nome , taxa_Frete , cozinha_id) values ('tuk tuk comida indiana' , 15.90 , 2 );

insert into tb_cidade (nome) values ('São paulo')
insert into tb_cidade (nome) values ('ilha bela')

insert into tb_estado (nome) values ('São paulo')
insert into tb_estado(nome) values ('Rio de janeiro')

insert into TB_FORMA_PAGAMENTO  (descricao) values ('cartão');
insert into TB_FORMA_PAGAMENTO  (descricao) values ('boleto');
insert into TB_FORMA_PAGAMENTO  (descricao) values ('dinheiro');

insert into tb_Restaurante_forma_pagamento (restaurante_id , forma_pagamento_id) values (1,2),(1,2),(2,2),(2,3),(3,3),(3,1);

insert into TB_PERMISSAO (nome , descricao) values ('kaique' , 'usuario permitido');
insert into TB_PERMISSAO (nome , descricao) values ('lucas' , 'usuario negado');