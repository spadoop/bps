---根据指定position_id获取组织下的所有人员
SELECT
  UA.ACCOUNT_ID as id
  , UA.ACCOUNT_NAME as name
  , 'person' as typecode
  --, O.ORG_ID
  --, O.ORG_CODE
  --, O.ORG_NAME
  --, OU.PL_ORG_TYPE 
FROM
  PL.PL_ORG_UNIT_USER@bps2pl_kmcs OU JOIN PL.PL_BIZ_TREE@bps2pl_kmcs BT 
    ON BT.TREE_ORG_ID = OU.TREE_ORG_ID JOIN PL.PL_ORG@bps2pl_kmcs O 
    ON O.ORG_ID = BT.ORG_ID JOIN PL.PL_USER_ACCOUNT@bps2pl_kmcs UA 
    ON UA.ACCOUNT_ID = OU.ACCOUNT_ID 
WHERE
  --O.ORG_CODE = '%s' 
  O.ORG_CODE = ? 
  AND BT.TREE_CODE = 'KMCS001001' 
  AND O.ORG_TYPE = 'PL00100003'
