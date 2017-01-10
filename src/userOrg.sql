---获取 人员所在机构
select dept.org_ID as id, dept.ORG_NAME as name, 'organization' as typeCode, active as active 
from PL.PL_ORG@bps2pl_kmcs dept, PL.PL_ORG_UNIT_USER@bps2pl_kmcs ou 
--where ou.ACCOUNT_ID = '%s' and ou.TREE_ORG_ID = dept.org_ID
where ou.ACCOUNT_ID = ? and ou.TREE_ORG_ID = dept.org_ID