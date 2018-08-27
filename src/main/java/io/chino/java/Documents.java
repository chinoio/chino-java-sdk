package io.chino.java;

import com.fasterxml.jackson.databind.JsonNode;
import io.chino.api.common.ChinoApiConstants;
import io.chino.api.common.ChinoApiException;
import io.chino.api.document.CreateDocumentRequest;
import io.chino.api.document.Document;
import io.chino.api.document.GetDocumentResponse;
import io.chino.api.document.GetDocumentsResponse;

import java.io.IOException;
import java.util.HashMap;

public class Documents extends ChinoBaseAPI {

    /**
     * The default constructor used by all {@link ChinoBaseAPI} subclasses
     *
     * @param baseApiUrl      the base URL of the Chino.io API. For testing, use:<br>
     *                        {@code https://api.test.chino.io/v1/}
     * @param parentApiClient the instance of {@link ChinoAPI} that created this object
     */
    public Documents(String baseApiUrl, ChinoAPI parentApiClient) {
        super(baseApiUrl, parentApiClient);
    }

    /**
     * List all the Documents in the specified {@link io.chino.api.schema.Schema Schema}. The results are paginated
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param offset page offset of the results.
     * @param limit the max amount of results to be returned
     *
     * @return a {@link GetDocumentsResponse} Object which contains the list of Documents.
     * The actual content of the Documents in the list   is not returned, but it can be read with {@link #read(String)}
     *
     * @see #read(String)
     * @see GetDocumentsResponse#getDocuments()
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetDocumentsResponse list(String schemaId, int offset, int limit) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        JsonNode data = getResource("/schemas/" + schemaId + "/documents", offset, limit);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentsResponse.class);
        return null;
    }

    /**
     * List all the Documents in the specified {@link io.chino.api.schema.Schema Schema}. The results are paginated
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     *
     * @return a {@link GetDocumentsResponse} Object which contains the list of Documents.
     * The actual content of the Documents in the list is not returned, but it can be read with {@link #read(String)}
     *
     * @see #read(String)
     * @see GetDocumentsResponse#getDocuments()
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetDocumentsResponse list(String schemaId) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        JsonNode data = getResource("/schemas/" + schemaId + "/documents", 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentsResponse.class);
        return null;
    }

    /**
     * List all the Documents in the specified {@link io.chino.api.schema.Schema Schema}. The results are paginated
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param offset page offset of the results.
     * @param limit the max amount of results to be returned
     * @param fullDocument if true, the content of all the Documents in the list will be fetched.
     *
     * @return a {@link GetDocumentsResponse} Object which contains the list of Documents.
     * The actual content of the Documents in the list is not returned, but it can be read with {@link #read(String)}
     *
     * @see #read(String)
     * @see GetDocumentsResponse#getDocuments()
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetDocumentsResponse list(String schemaId, int offset, int limit, boolean fullDocument) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        JsonNode data;
        if(fullDocument) {
            data = getResource("/schemas/" + schemaId + "/documents?full_document=true");
        } else {
            data = getResource("/schemas/" + schemaId + "/documents", offset, limit);
        }
        if(data!=null)
            return mapper.convertValue(data, GetDocumentsResponse.class);
        return null;
    }

    /**
     * List all the Documents in the specified {@link io.chino.api.schema.Schema Schema}. The results are paginated
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param fullDocument if true, the content of all the Documents in the list will be fetched.
     *
     * @return a {@link GetDocumentsResponse} Object which contains the list of Documents.
     * The actual content of the Documents in the list is not returned, but it can be read with {@link #read(String)}
     *
     * @see #read(String)
     * @see GetDocumentsResponse#getDocuments()
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public GetDocumentsResponse list(String schemaId, boolean fullDocument) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        JsonNode data;
        if(fullDocument) {
            data = getResource("/schemas/" + schemaId + "/documents?full_document=true");
        } else {
            data = getResource("/schemas/" + schemaId + "/documents", 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        }
        if(data!=null)
            return mapper.convertValue(data, GetDocumentsResponse.class);
        return null;
    }

    /**
     * Read a specific {@link Document} from Chino.io
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     *
     * @return a {@link Document} object.
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document read(String documentId) throws IOException, ChinoApiException{
        checkNotNull(documentId, "document_id");
        JsonNode data = getResource("/documents/"+documentId, 0, ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentResponse.class).getDocument();

        return null;
    }

    /**
     * Map the content of a specific {@link Document} from Chino.io to a compatible Java object.
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param myClass a {@link Class} whose attributes match the structure of the Document. The data returned by Chino.io
     *                will be mapped on an instance of this class before being returned.
     *
     * @return an instance of {@code myClass}, where the retrieved Document has been mapped.
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Object read(String documentId, Class myClass) throws IOException, ChinoApiException{
        checkNotNull(documentId, "document_id");
        checkNotNull(myClass, "my_class");
        JsonNode data = getResource("/documents/"+documentId, 0 , ChinoApiConstants.QUERY_DEFAULT_LIMIT);
        if(data!=null) {
            Document document = mapper.convertValue(data, GetDocumentResponse.class).getDocument();
            return mapper.convertValue(document.getContent(), myClass);
        }
        return null;
    }

    /**
     * Create a new {@link Document} with the specified content on Chino.io. (synchronous)
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param content a {@link HashMap} with the content of the new Document.
     *                The map's keys must match the fields of the Schema.
     * @param consistent setting this flag to {@code true} will make the indexing synchronous with the creation of the document,
     *                   i.e. search operations will be successful right after this method returns.
     *                   However, this operation has a cost and can make the API call last for seconds before answering.
     *                   Use only when it's really needed
     *
     * @return the metadata of a new Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document create(String schemaId, HashMap content, boolean consistent) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest(content);
        String URL = "/schemas/" + schemaId + "/documents" + ((consistent) ? "?consistent=true" : "");
        JsonNode data = postResource(URL, createDocumentRequest);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentResponse.class).getDocument();

        return null;
    }

    /**
     * Create a new {@link Document} with the specified content on Chino.io.
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param content a {@link HashMap} with the content of the new Document.
     *                The map's keys must match the fields of the Schema.
     *
     * @return the metadata of a new Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document create(String schemaId, HashMap content) throws IOException, ChinoApiException {
        return create(schemaId, content, false);
    }

    /**
     * Create a new {@link Document} with the specified content on Chino.io. (synchronous)
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param content a {@link String} with the content of the new Document in JSON format.
     *                The structure of the JSON must match the one of the Schema.
     * @param consistent setting this flag to {@code true} will make the indexing synchronous with the creation of the document,
     *                   i.e. search operations will be successful right after this method returns.
     *                   However, this operation has a cost and can make the API call last for seconds before answering.
     *                   Use only when it's really needed
     *
     * @return the metadata of a new Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document create(String schemaId, String content, boolean consistent) throws IOException, ChinoApiException {
        checkNotNull(schemaId, "schema_id");
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest(fromStringToHashMap(content));
        String URL = "/schemas/" + schemaId + "/documents" + ((consistent) ? "?consistent=true" : "");
        JsonNode data = postResource(URL, createDocumentRequest);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentResponse.class).getDocument();

        return null;
    }

    /**
     * Create a new {@link Document} with the specified content on Chino.io.
     *
     * @param schemaId the id of an existing {@link io.chino.api.schema.Schema Schema}
     * @param content a {@link String} with the content of the new Document in JSON format.
     *                The structure of the JSON must match the one of the Schema.
     *
     * @return the metadata of a new Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document create(String schemaId, String content) throws IOException, ChinoApiException {
        return create(schemaId, content, false);
    }

    /**
     * Update a specific {@link Document} on Chino.io with new data. (synchronous)
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param content a {@link HashMap} with the content of the new Document.
     *                The map's keys must match the fields of the Schema.
     * @param consistent setting this flag to {@code true} will make the indexing synchronous with the update,
     *                   i.e. search operations will be successful right after this method returns.
     *                   However, this operation has a cost and can make the API call last for seconds before answering.
     *                   Use only when it's really needed
     *
     * @return the metadata of the updated Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document update(String documentId, HashMap content, boolean consistent) throws IOException, ChinoApiException {
        checkNotNull(documentId, "document_id");
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest(content);
        String URL = "/documents/" + documentId + ((consistent) ? "?consistent=true" : "");
        JsonNode data = putResource(URL, createDocumentRequest);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentResponse.class).getDocument();

        return null;
    }

    /**
     * Update a specific {@link Document} on Chino.io with new data.
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param content a {@link HashMap} with the content of the new Document.
     *                The map's keys must match the fields of the Schema.
     *
     * @return the metadata of the updated Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document update(String documentId, HashMap content) throws IOException, ChinoApiException {
        return update(documentId, content, false);
    }

    /**
     * Update a specific {@link Document} on Chino.io with new data. (synchronous)
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param content a {@link String} with the content of the new Document in JSON format.
     *                The structure of the JSON must match the one of the Schema.
     * @param consistent setting this flag to {@code true} will make the indexing synchronous with the update,
     *                   i.e. search operations will be successful right after this method returns.
     *                   However, this operation has a cost and can make the API call last for seconds before answering.
     *                   Use only when it's really needed
     *
     * @return the metadata of the updated Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public Document update(String documentId, String content, boolean consistent) throws IOException, ChinoApiException {
        checkNotNull(documentId, "document_id");
        CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest(fromStringToHashMap(content));
        String URL = "/documents/" + documentId + ((consistent) ? "?consistent=true" : "");
        JsonNode data = putResource(URL, createDocumentRequest);
        if(data!=null)
            return mapper.convertValue(data, GetDocumentResponse.class).getDocument();

        return null;
    }

    /**
     * Update a specific {@link Document} on Chino.io with new data.
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param content a {@link String} with the content of the new Document in JSON format.
     *                The structure of the JSON must match the one of the Schema.
     *
     * @return the metadata of the updated Document. <b>NOTE: the Document's content will NOT be returned.</b>
     *         It can be set with {@link Document#setContent(HashMap)} or fetched from Chino.io with {@link #read(String) read(documentId)}
     *
     * @throws IOException
     * @throws ChinoApiException
     */

    public Document update(String documentId, String content) throws IOException, ChinoApiException {
        return update(documentId, content, false);
    }

    /**
     * Delete a specific {@link Document} from Chino.io
     *
     * @param documentId the id of an existing {@link Document}. IDs can be retrieved using one of the 'list' methods,
     *                   e.g. {@link #list(String) list(schemaId)}
     * @param force if set to {@code false}, the Document becomes inactive and can be restored in future.
     *              If {@code true}, the Document is deleted and will be lost forever.
     *
     * @return a {@link String} containing either a success message or an error
     *
     * @throws IOException
     * @throws ChinoApiException
     */
    public String delete(String documentId, boolean force) throws IOException, ChinoApiException {
        checkNotNull(documentId, "document_id");
        return deleteResource("/documents/"+documentId, force);
    }
}
