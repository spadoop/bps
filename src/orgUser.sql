---根据指定dept_id获取组织下的所有人员
select distinct id, name, typecode  from
(
select t.user_ID as ID, t.login_name as NAME, 'person' as TYPECODE  
from PL.PL_USER@bps2pl_kmcs t, PL.PL_ORG_UNIT_USER@bps2pl_kmcs ou
--where ou.TREE_ORG_ID = '%s'  and t.user_ID = ou.ACCOUNT_ID and t.active=1
where ou.TREE_ORG_ID = ?  and t.user_ID = ou.ACCOUNT_ID and t.active=1
order by t.login_name
)