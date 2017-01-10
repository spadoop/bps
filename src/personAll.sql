---获取所有的人员对象
--select distinct id, name, TYPECODE from
--(
--select t1.user_ID as ID, t1.login_name as NAME, 'person' as TYPECODE 
--from PL.PL_USER t1 where active=1
--order by login_name
--)
select distinct id, name, TYPECODE from
(
select t1.account_ID as ID, t1.account_name as NAME, 'person' as TYPECODE 
from PL.PL_USER_account@bps2pl_kmcs t1  where DELETED_FLAG='0'
order by account_name
)