package io.chino.android;

import com.fasterxml.jackson.databind.JsonNode;
import io.chino.api.collection.Collection;
import io.chino.api.collection.CreateCollectionRequest;
import io.chino.api.collection.GetCollectionResponse;
import io.chino.api.collection.GetCollectionsResponse;
import io.chino.api.common.ChinoApiConstants;
import io.chino.api.common.ChinoApiException;
import io.chino.api.document.GetDocumentsResponse;
import okhttp3.OkHttpClient;
import java.io.IOException;

public class Collections extends ChinoBaseAPI {
    public Collections(String hostUrl, OkHttpClient clientInitialized){
        super(hostUrl, clientInitialized);
    }

    /**
     * Used to get a list of Collections
     * @param offset the offset
     * @return a GetCollectionsResponse Object
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetCollectionsResponse list(int offset) throws IOException, ChinoApiException {
        JsonNode data = getResource("/collections", offset, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetCollectionsResponse.class);
        return null;
    }

    /**
     * Used to get a single Collection
     * @param collectionId the id of the Collection to read
     * @return a Collection Object
     * @throws IOException
     * @throws ChinoApiException
     */
    public Collection read(String collectionId) throws IOException, ChinoApiException{
        JsonNode data = getResource("/collections/"+collectionId, 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetCollectionResponse.class).getCollection();

        return null;
    }

    /**
     * Used to create a new Collection
     * @param name the name of the Collection
     * @return a Collection Object
     * @throws IOException
     * @throws ChinoApiException
     */
    public Collection create(String name) throws IOException, ChinoApiException {
        CreateCollectionRequest collectionRequest = new CreateCollectionRequest();
        collectionRequest.setName(name);
        JsonNode data = postResource("/collections", collectionRequest);
        if(data!=null)
            return mapper.convertValue(data, GetCollectionResponse.class).getCollection();

        return null;
    }

    /**
     * Used to update a Collection
     * @param collectionId the id of the Collection to update
     * @param name the name of the new Collection
     * @return a Collection Object
     * @throws IOException
     * @throws ChinoApiException
     */
    public Collection update(String collectionId, String name) throws IOException, ChinoApiException {
        CreateCollectionRequest collectionRequest = new CreateCollectionRequest();
        collectionRequest.setName(name);

        JsonNode data = putResource("/collections/"+collectionId, collectionRequest);
        if(data!=null)
            return mapper.convertValue(data, Collection.class);
        return null;
    }

    /**
     * Used to get a list of Documents in a Collection
     * @param collectionId the id of the Collection
     * @param offset the offset
     * @return a GetDocumentsResponse Object
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetDocumentsResponse listDocuments(String collectionId, int offset)throws IOException, ChinoApiException {
        JsonNode data = getResource("/collections/"+collectionId+"/documents", offset, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentsResponse.class);
        return null;
    }

    /**
     * Used to add a new Document to the Collection
     * @param collectionId the id of the Collection
     * @param documentId the id of the Document
     * @return a String that represents the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String addDocument(String collectionId, String documentId)throws IOException, ChinoApiException {
        postResource("/collections/"+collectionId+"/documents/"+documentId, null);
        return "success";
    }

    /**
     * Used to remove a Document from a Collection
     * @param collectionId the id of the Collection
     * @param documentId the id of the Document
     * @return a String that represents the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String removeDocument(String collectionId, String documentId)throws IOException, ChinoApiException {
        deleteResource("/collections/"+collectionId+"/documents/"+documentId, false);
        return "success";
    }

    /**
     * Used to delete a Collection
     * @param collectionId the id of the Collection
     * @param force the boolean force
     * @return a String that represents the result of the operation
     * @throws IOException
     * @throws ChinoApiException
     */
    public String delete(String collectionId, boolean force) throws IOException, ChinoApiException {
        return deleteResource("/collections/"+collectionId, force);
    }

}
