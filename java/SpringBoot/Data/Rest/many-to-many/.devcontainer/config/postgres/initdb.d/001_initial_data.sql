insert into item (id, item_name)
values
    (1, 'id'),
    (2, 'ic'),
    (3, 'ib'),
    (4, 'ia');

insert into torder (id, order_name)
values
    (1, 'od'),
    (2, 'oc'),
    (3, 'ob'),
    (4, 'oa');

insert into item_order (id, order_id, item_id)
values
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 4),
    (4, 4, 3);