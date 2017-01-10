----获取所有角色
select distinct id, name, typecode  from
(
select R.role_id as ID, R.role_name as NAME, 'role' as TYPECODE  
from PL.PL_ROLE@bps2pl_kmcs R
)