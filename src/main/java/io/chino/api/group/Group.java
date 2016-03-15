
package io.chino.api.group;

import java.util.Date;

import io.chino.client.ChinoBaseAPI;
import org.codehaus.jackson.JsonNode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.chino.client.ChinoAPI;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "insert_date",
    "is_active",
    "last_update",
    "group_name",
    "attributes",
    "group_id"
})
public class Group {

    @JsonProperty("insert_date")
    private Date insertDate;
    @JsonProperty("is_active")
    private Boolean isActive;
    @JsonProperty("last_update")
    private Date lastUpdate;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("attributes")
    private JsonNode attributes;
    @JsonProperty("group_id")
    private String groupId;

    /**
     * 
     * @return
     *     The insertDate
     */
    @JsonProperty("insert_date")
    public Date getInsertDate() {
        return insertDate;
    }

    /**
     * 
     * @param insertDate
     *     The insert_date
     */
    @JsonProperty("insert_date")
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    /**
     * 
     * @return
     *     The isActive
     */
    @JsonProperty("is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * 
     * @param isActive
     *     The is_active
     */
    @JsonProperty("is_active")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 
     * @return
     *     The lastUpdate
     */
    @JsonProperty("last_update")
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * 
     * @param lastUpdate
     *     The last_update
     */
    @JsonProperty("last_update")
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * 
     * @return
     *     The group_name
     */
    @JsonProperty("group_name")
    public String getGroupName() {
        return groupName;
    }

    /**
     * 
     * @param groupName
     *     The groupName
     */
    @JsonProperty("group_name")
    public void setGroupname(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 
     * @return
     *     The attributes
     */
    @JsonProperty("attributes")
    public JsonNode getAttributes() {
        return attributes;
    }

    /**
     * 
     * @param attributes
     *     The attributes
     */
    @JsonProperty("attributes")
    public void setAttributes(JsonNode attributes) {
        this.attributes = attributes;
    }

    /**
     * 
     * @return
     *     The groupId
     */
    @JsonProperty("group_id")
    public String getGroupId() {
        return groupId;
    }

    /**
     * 
     * @param groupId
     *     The group_id
     */
    @JsonProperty("group_id")
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	

    public String toString(){
    	String s="";
    	s+="group_name: "+groupName;
    	s+=",\ngroup_id: "+groupId;
    	s+=",\ninsert_date: "+insertDate;
    	try {
			s+=",\nattributes: "+ ChinoBaseAPI.getMapper().writeValueAsString(attributes);
		} catch (Exception e) {} 
    	s+=",\nis_active: "+isActive;
    	s+=",\nlast_update: "+lastUpdate;
       	 
    	return s;
    }

}