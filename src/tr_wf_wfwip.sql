create or replace trigger tr_wf_wfwip
  after update on wfprocessinst  
  for each row
begin
  update WFWORKITEM t set t.PROCESSINSTNAME=:new.processinstname where t.PROCESSINSTID=:new.processinstid;
  update WFWIPARTICIPANT t set t.PROCESSINSTNAME=:new.processinstname where t.PROCESSINSTID=:new.processinstid;
end tr_wf_wfwip;