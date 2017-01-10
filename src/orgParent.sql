--根据指定的dept_id获取上级组织对象
select dept_parent.dept_id as id, dept_parent.dept_name as name, 'organization' as typeCode
from V_INFO_DEPARTMENT dept, V_INFO_DEPARTMENT dept_parent
where dept.parent_id = dept_parent.dept_id
--and dept.dept_id = '%s'
and dept.dept_id = ?