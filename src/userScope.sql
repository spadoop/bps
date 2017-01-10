---获取用户的范围，即用户所属的组织、角色、岗位和用户本身
 
select dept.org_ID as id, dept.ORG_NAME as name, 'organization' as typeCode, dept.DELETED_FLAG as active
from pl.PL_ORG@bps2pl_kmcs dept, pl.PL_ORG_UNIT_USER@bps2pl_kmcs ou
--where ou.ACCOUNT_ID = '%s' and ou.TREE_ORG_ID = dept.org_ID 
where ou.ACCOUNT_ID = ? and ou.TREE_ORG_ID = dept.org_ID 
union all
select r.role_ID as id, r.role_name as name, 'role' as typeCode, r.DELETED_FLAG AS active
from  pl.PL_ROLE@bps2pl_kmcs r , pl.PL_USER_ROLE@bps2pl_kmcs ur
--where ur.ACCOUNT_ID = '%s' and ur.PL_ROLE_ID = r.role_ID 
where ur.ACCOUNT_ID = ? and ur.PL_ROLE_ID = r.role_ID 
union all
select person.ACCOUNT_ID as id, person.account_name as name, 'person' as typeCode, person.DELETED_FLAG as active
from pl.PL_USER_account@bps2pl_kmcs person
--where  person.ACCOUNT_ID = '%s'
where  person.ACCOUNT_ID = ?
union all
SELECT
  --UA.ACCOUNT_ID as id
  --, UA.ACCOUNT_NAME as name
  --, 'person' as typecode
  --, O.ORG_ID
   O.ORG_CODE as id
  , O.ORG_NAME as name
  , 'position' as typeCode
  , O.DELETED_FLAG as active
  --, OU.PL_ORG_TYPE 
FROM
  PL.PL_ORG_UNIT_USER@bps2pl_kmcs OU JOIN PL.PL_BIZ_TREE@bps2pl_kmcs BT 
    ON BT.TREE_ORG_ID = OU.TREE_ORG_ID JOIN PL.PL_ORG@bps2pl_kmcs O 
    ON O.ORG_ID = BT.ORG_ID JOIN PL.PL_USER_ACCOUNT@bps2pl_kmcs UA 
    ON UA.ACCOUNT_ID = OU.ACCOUNT_ID 
WHERE
  --UA.ACCOUNT_ID = '%s' 
  UA.ACCOUNT_ID = ? 
  AND BT.TREE_CODE = 'KMCS001001' 
  AND O.ORG_TYPE = 'PL00100003'
