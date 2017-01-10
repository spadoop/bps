----获取所有角色，本应为职位信息，但目前先用角色代替
select distinct
  id
  , name
  , typecode 
from
  ( 
    select
      o.org_name as name
      , o.org_code as id
      , 'position' as TYPECODE 
    from
      pl.pl_biz_tree@bps2pl_kmcs bt 
      left join pl.pl_org@bps2pl_kmcs o 
        on bt.org_id = o.org_id 
    where
      o.org_type = 'PL00100003' 
      and bt.tree_code = 'KMCS001001'
  ) 
