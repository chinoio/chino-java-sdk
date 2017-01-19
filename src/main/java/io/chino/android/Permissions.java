package io.chino.android;

import com.fasterxml.jackson.databind.JsonNode;
import io.chino.api.common.ChinoApiConstants;
import io.chino.api.common.ChinoApiException;
import io.chino.api.permission.GetPermissionsResponse;
import io.chino.api.permission.PermissionRule;
import io.chino.api.permission.PermissionRuleCreatedDocument;
import okhttp3.OkHttpClient;
import java.io.IOException;

public class Permissions extends ChinoBaseAPI {
    public Permissions(String hostUrl, OkHttpClient clientInitialized){
        super(hostUrl, clientInitialized);
    }

    /**
     * It reads Permissions on all Resources
     * @param offset the offset from which it retrieves the Permissions
     * @param limit number of results (max {@link io.chino.api.common.ChinoApiConstants#QUERY_DEFAULT_LIMIT})
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissions(int offset, int limit) throws IOException, ChinoApiException {
        JsonNode data = getResource("/perms", offset, limit);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }

    /**
     * It reads Permissions on all Resources
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissions() throws IOException, ChinoApiException {
        JsonNode data = getResource("/perms", 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }


    /**
     * It reads Permissions on a Document
     * @param documentId the id of the Document
     * @param offset the offset from which it retrieves the Permissions
     * @param limit number of results (max {@link io.chino.api.common.ChinoApiConstants#QUERY_DEFAULT_LIMIT})
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOnaDocument(String documentId, int offset, int limit) throws IOException, ChinoApiException {
        JsonNode data = getResource("/perms/documents/"+documentId, offset, limit);
        if(data!=null) {
            return mapper.convertValue(data, GetPermissionsResponse.class);
        }
        return null;
    }

    /**
     * It reads Permissions on a Document
     * @param documentId the id of the Document
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOnaDocument(String documentId) throws IOException, ChinoApiException {
        JsonNode data = getResource("/perms/documents/"+documentId, 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null) {
            return mapper.convertValue(data, GetPermissionsResponse.class);
        }
        return null;
    }

    /**
     * Used to read Permissions of a User
     * @param userId the id of the User
     * @param offset the offset from which it retrieves the Permissions
     * @param limit number of results (max {@link io.chino.api.common.ChinoApiConstants#QUERY_DEFAULT_LIMIT})
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOfaUser(String userId, int offset, int limit) throws IOException, ChinoApiException{
        JsonNode data = getResource("/perms/users/"+userId, offset, limit);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }

    /**
     * Used to read Permissions of a User
     * @param userId the id of the User
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOfaUser(String userId) throws IOException, ChinoApiException{
        JsonNode data = getResource("/perms/users/"+userId, 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }

    /**
     * Used to read Permissions of a Group
     * @param groupId the id of the Group
     * @param offset the offset from which it retrieves the Permissions
     * @param limit number of results (max {@link io.chino.api.common.ChinoApiConstants#QUERY_DEFAULT_LIMIT})
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOfaGroup(String groupId, int offset, int limit) throws IOException, ChinoApiException{
        JsonNode data = getResource("/perms/groups/"+groupId, offset, limit);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }

    /**
     * Used to read Permissions of a Group
     * @param groupId the id of the Group
     * @return GetPermissionsResponse Object with a list of Permissions
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetPermissionsResponse readPermissionsOfaGroup(String groupId) throws IOException, ChinoApiException{
        JsonNode data = getResource("/perms/groups/"+groupId, 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetPermissionsResponse.class);

        return null;
    }

    /**
     * It creates Permissions on Resources
     * @param action a String that specifies the action
     * @param resourceType the type of the Resources
     * @param subjectType the type of the Subject
     * @param subjectId the id of the Subject
     * @param permissionRule the PermissionRule Object
     * @return a String with the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String permissionsOnResources(String action, String resourceType, String subjectType, String subjectId, PermissionRule permissionRule) throws IOException, ChinoApiException {
        postResource("/perms/"+action+"/"+resourceType+"/"+subjectType+"/"+subjectId, permissionRule);
        return "success";
    }

    /**
     * It creates Permissions on a Resource
     * @param action a String that specifies the action
     * @param resourceType the type of the Resource
     * @param resourceId the id of the Resource
     * @param subjectType the type of the Subject
     * @param subjectId the id of the Subject
     * @param permissionRule the PermissionRule Object
     * @return a String with the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String permissionsOnaResource(String action, String resourceType, String resourceId, String subjectType, String subjectId, PermissionRule permissionRule) throws IOException, ChinoApiException {
        postResource("/perms/"+action+"/"+resourceType+"/"+resourceId+"/"+subjectType+"/"+subjectId, permissionRule);
        return "success";
    }

    /**
     * It creates Permissions on a Resource Children
     * @param action a String that specifies the action
     * @param resourceType the type of the Resource
     * @param resourceId the id of the Resource
     * @param resourceChildren the type of the ResourceChildren
     * @param subjectType the type of the Subject
     * @param subjectId the id of the Subject
     * @param permissionRule the PermissionRule Object
     * @return a String with the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String permissionsOnResourceChildren(String action, String resourceType, String resourceId, String resourceChildren, String subjectType, String subjectId, PermissionRule permissionRule) throws IOException, ChinoApiException {
        postResource("/perms/"+action+"/"+resourceType+"/"+resourceId+"/"+resourceChildren+"/"+subjectType+"/"+subjectId, permissionRule);
        return "success";
    }

    /**
     * It creates Permissions on a Resource Children
     * @param action a String that specifies the action
     * @param resourceType the type of the Resource
     * @param resourceId the id of the Resource
     * @param resourceChildren the type of the ResourceChildren
     * @param subjectType the type of the Subject
     * @param subjectId the id of the Subject
     * @param permissionRule the PermissionRuleCreatedDocument Object
     * @return a String with the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String permissionsOnResourceChildren(String action, String resourceType, String resourceId, String resourceChildren, String subjectType, String subjectId, PermissionRuleCreatedDocument permissionRule) throws IOException, ChinoApiException {
        postResource("/perms/"+action+"/"+resourceType+"/"+resourceId+"/"+resourceChildren+"/"+subjectType+"/"+subjectId, permissionRule);
        return "success";
    }

}
