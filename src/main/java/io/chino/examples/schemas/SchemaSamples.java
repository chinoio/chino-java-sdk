package io.chino.examples.schemas;

import io.chino.api.common.ChinoApiException;
import io.chino.api.common.Field;
import io.chino.api.repository.Repository;
import io.chino.api.schema.Schema;
import io.chino.api.schema.SchemaRequest;
import io.chino.api.schema.SchemaStructure;
import io.chino.java.ChinoAPI;
import io.chino.test.util.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SchemaSamples {

    String SCHEMA_ID1 = "";
    String SCHEMA_ID2 = "";
    String SCHEMA_ID3 = "";
    String REPOSITORY_ID = "";
    ChinoAPI chino;

    public void testSchemas() throws IOException, ChinoApiException {
        //We initialize the ChinoAPI variable with the customerId and customerKey
        chino = new ChinoAPI(Constants.HOST, Constants.CUSTOMER_ID, Constants.CUSTOMER_KEY);

        //First we need to create a Repository
        Repository repository = chino.repositories.create("test_repository");
        REPOSITORY_ID = repository.getRepositoryId();

        //Check CREATE Schema with all three methods

        //First method, passing the id of the Repository and a SchemaRequest
        SchemaRequest schemaRequest = new SchemaRequest();
        schemaRequest.setDescription("schema_description1");
        SchemaStructure schemaStructure = new SchemaStructure();
        List<Field> fields = new ArrayList<Field>();
        fields.add(new Field("test_string1", "string"));
        fields.add(new Field("test_integer1", "integer"));
        fields.add(new Field("test_date1", "date"));
        schemaStructure.setFields(fields);
        schemaRequest.setStructure(schemaStructure);
        Schema schema1 = chino.schemas.create(REPOSITORY_ID, schemaRequest);
        SCHEMA_ID1 = schema1.getSchemaId();
        System.out.println(schema1);

        //Second method, passing the id of the Repository, a description and a SchemaStructure
        schemaStructure = new SchemaStructure();
        fields = new ArrayList<Field>();
        fields.add(new Field("test_string2", "string"));
        fields.add(new Field("test_integer2", "integer"));
        fields.add(new Field("test_date2", "date"));
        schemaStructure.setFields(fields);
        Schema schema2 = chino.schemas.create(REPOSITORY_ID, "schema_description2", schemaStructure);
        SCHEMA_ID2 = schema2.getSchemaId();
        System.out.println(schema2);

        //Third method, passing the id of the Repository, a description and a Class with the variables we want as Fields
        Schema schema = chino.schemas.create(REPOSITORY_ID, "schema_description3", SchemaStructureSample.class);
        SCHEMA_ID3 = schema.getSchemaId();
        System.out.println(schema);

        //Check UPDATE Schema first method
        schemaRequest = new SchemaRequest();
        schemaRequest.setDescription("schema_description1_updated");
        schemaStructure = new SchemaStructure();
        fields = new ArrayList<Field>();
        fields.add(new Field("test_string1", "string"));
        fields.add(new Field("test_integer1", "integer"));
        fields.add(new Field("test_date1", "date"));
        fields.add(new Field("test_boolean", "boolean"));
        schemaStructure.setFields(fields);
        schemaRequest.setStructure(schemaStructure);
        System.out.println(chino.schemas.update(SCHEMA_ID1, schemaRequest));

        //Check UPDATE Schema second method
        schemaStructure = new SchemaStructure();
        fields = new ArrayList<Field>();
        fields.add(new Field("test_string2", "string"));
        fields.add(new Field("test_integer2", "integer"));
        fields.add(new Field("test_date2", "date"));
        fields.add(new Field("test_boolean", "boolean"));
        schemaStructure.setFields(fields);
        System.out.println(chino.schemas.update(SCHEMA_ID2, "schema_description2_updated", schemaStructure));

        //Check UPDATE Schema third method
        System.out.println(chino.schemas.update(SCHEMA_ID3, "schema_description3_updated", SchemaStructureSampleUpdated.class));

        //Check LIST Schemas
        System.out.println(chino.schemas.list(REPOSITORY_ID));

        //Check DELETE Schemas
        System.out.println(chino.schemas.delete(SCHEMA_ID1, true));
        System.out.println(chino.schemas.delete(SCHEMA_ID2, true));
        System.out.println(chino.schemas.delete(SCHEMA_ID3, true));

        //Finally we delete the Repository too
        System.out.println(chino.repositories.delete(REPOSITORY_ID, true));
    }
}
