---获取所有的人员对象
select distinct id, name, TYPECODE from
(
select t1.user_ID as ID, t1.login_name as NAME, 'person' as TYPECODE 
from PL.PL_USER@bps2pl_kmcs t1 , PL.PL_ORG_UNIT_USER@bps2pl_kmcs ou, PL.PL_USER_ROLE@bps2pl_kmcs ur, PL.PL_ROLE@bps2pl_kmcs r
where t1.user_ID=ou.ACCOUNT_ID and ou.TREE_ORG_ID = '%s' 
and t1.user_ID=ur.PL_USER_ID and ur.PL_ROLE_ID=r.ID and r.role_CODE = '%s' 
and t1.active=1 and r.active=1
order by login_name
)