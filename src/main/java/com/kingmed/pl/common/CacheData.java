package com.kingmed.pl.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eos.workflow.omservice.WFParticipant;

public class CacheData {

	public static Map<String, WFParticipant> findParticipantByIDWithPerson;
	
	public static Map<String, WFParticipant> findParticipantByIDWithPosition;
	
	public static Map<String, List<WFParticipant>> findParticipantScopeWithPerson;
	
	static{
		findParticipantByIDWithPerson = new HashMap<String, WFParticipant>();
		findParticipantByIDWithPosition = new HashMap<String, WFParticipant>();
		findParticipantScopeWithPerson = new HashMap<String, List<WFParticipant>>();
	}
	
	public static WFParticipant getFindParticipantByIDWithPerson(String accountId){
		return findParticipantByIDWithPerson.get(accountId);
	}
	
	public static void setFindParticipantByIDWithPerson(String accountId, WFParticipant p){
		findParticipantByIDWithPerson.put(accountId, p);
	}
	
	public static WFParticipant getFindParticipantByIDWithPosition(String positionCode){
		return findParticipantByIDWithPosition.get(positionCode);
	}
	
	public static void setFindParticipantByIDWithPosition(String positionCode, WFParticipant p){
		findParticipantByIDWithPosition.put(positionCode, p);
	}

	public static List<WFParticipant> getFindParticipantScopeWithPerson(String accountId) {
		return findParticipantScopeWithPerson.get(accountId);
	}

	public static void setFindParticipantScopeWithPerson(String accountId, List<WFParticipant> participants) {
		findParticipantScopeWithPerson.put(accountId, participants);
	}
	
	public static void reset(){
		findParticipantByIDWithPerson.clear();
		findParticipantByIDWithPosition.clear();
		findParticipantScopeWithPerson.clear();
	}
}
