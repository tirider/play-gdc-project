package models.dao;

import java.util.HashMap;
import java.util.Map;


/**
 * Class Neo4jProvider
 */
public class Neo4j 
{
	/*public static final String NL = System.getProperty("line.separator") ;
	private static final String DB_PATH = "data/neo4j/graph.db";
	
	private HashMap<String,String[]> nodesAndProperties;
	private GraphDatabaseService graphDb;
	private String nodeResult="";
	private HashMap<String, String> rdfMap = new HashMap<String, String>();
	private ExecutionEngine engine;
	private ExecutionResult result;

	public Neo4j(HashMap<String, String[]> nodesAndProperties) 
	{
		super();
		this.nodesAndProperties = nodesAndProperties;
	}
	

	/**
	 * @return       Model
	 * @param        nameSpace
	 
	public void generateRdf( String nameSpace, String prefixe)
	{
		//clearDb();
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
		// START SNIPPET: execute
		engine = new ExecutionEngine( graphDb );

		

		try ( Transaction ignored = graphDb.beginTx() )
		{
			//result = engine.execute( "start n=node:Movie  return n, n.name" );
			result = engine.execute( "match(node1)-[relation]->(node2) return labels(node1) as n1,node1 as id1, type(relation) as r,labels(node2) as n2,node2 as id2" );
			// END SNIPPET: execute
			// START SNIPPET: items
			
			String rdf="<rdf:RDF>"+NL; 
			String n1 ="";
			String n2="";
			String r="";
			String id1="";
			String id2="";


			for ( Map<String, Object> row : result )
			{
				for ( Entry<String, Object> column : row.entrySet() )
				{
					//nodeResult += column.getKey() + ": " + column.getValue() + "; ";

					switch (column.getKey()) {
					case "r": 
						r=column.getValue().toString();
						break;

					case "n1": 
						n1=removeBraketsHelper(column.getValue().toString());
						break;

					case "n2": 
						n2=removeBraketsHelper(column.getValue().toString());
						break;

					case "id1": 
						id1 =removeBraketsHelper(column.getValue().toString());
						break;

					case "id2": 
						id2=removeBraketsHelper(column.getValue().toString());
						break;

					default:																																																														
						break;
					}
				}
				if(!rdfMap.containsKey(n2+id2)){

					addDescription( n2, id2,nameSpace, prefixe);
				}
				if(!rdfMap.containsKey(n1+id1)){
					addDescription( n1, id1,nameSpace, prefixe);
				}

				rdfMap.put(n1+id1, rdfMap.get(n1+id1)+generateRdfAddRelation(r, n2, id2, nameSpace, prefixe));



				//nodeResult += "\n";
			}

			rdf+=unMap();
			
			rdf+="</rdf:RDF>";
			System.out.println(rdf);
			//System.out.println(nodeResult);
			// END SNIPPET: items
		}
	}
	private String unMap(){
		String rdf="";
		Iterator<String> it = rdfMap.values().iterator();
		String strKey = null;
		while (it.hasNext()) {
			String value = it.next();
			for(Map.Entry entry: rdfMap.entrySet()){
				if(value.equals(entry.getValue())){
					strKey = (String) entry.getKey();
					break; //breaking because its one to one map
				}
			}
			rdfMap.put(strKey, value+closeDescription());
			rdf+=rdfMap.get(strKey);


		}
		return rdf;
		
	}
	private void addDescription(String node, String id, String nameSpace, String prefixe){
		rdfMap.put(node+id, generateRdfAddNode(node, id, nameSpace));
		Map<String, Object> params = new HashMap<String, Object>();
		System.out.println(id);
		params.put( "id", Integer.parseInt(id) );
		System.out.println("id : "+params.get("id"));
		String[] properties = nodesAndProperties.get(node);
		
		for (int i = 0; i < properties.length; i++) {
			String propName = getPropertyNameHelper(properties[i]);
            String propType = getPropertyTypeHelper(properties[i]);
            String propValue;
			String query = "START n=node({id}) RETURN n, n."+propName;
			 result = engine.execute( query, params);
			System.out.println(query);
			Iterator<Node> n_column = result.columnAs( "n");
            for ( Node n : IteratorUtil.asIterable( n_column ) )
            {
            	propValue = n.getProperty(propName).toString();
            	rdfMap.put(node+id, rdfMap.get(node+id)+generateRdfAddDataProperty(propName, propValue, propType, nameSpace,prefixe));
            }
		}
	}
	
	private String getPropertyNameHelper(String prop){
		return prop.split(";")[0];
	}
	private String getPropertyTypeHelper(String prop){
		return prop.split(";")[1];
	}

	private String generateRdfAddRelation(String r, String n2, String id2, String nameSpace, String prefixe) {
		// TODO Auto-generated method stub
		String rdf="";
		rdf+="\t\t<"+prefixe+":"+r+" rdf:resource=\""+nameSpace+"#"+n2+id2+"\" />"+NL;
		return rdf;
	}

	private String generateRdfAddDataProperty(String propName, String propValue, String propType, String nameSpace, String prefixe) {
		// TODO Auto-generated method stub
		String rdf="";
		rdf+="\t\t<"+prefixe+":"+propName+" rdf:datatype=\"http://www.w3.org/2001/XMLSchema#"+propType+"\">"+ propValue+"</"+propName+">"+NL;
		return rdf;
	}

	private String removeBraketsHelper(String str){
		String res = str.substring(str.indexOf('[')+1, str.indexOf(']'));

		return res;

	}

	private String generateRdfAddNode(String node, String id, String nameSpace){
		String rdf ="";
		rdf+="\t<rdf:Description rdf:about=\""+nameSpace+"#"+node+id+"\" >"+NL;
		rdf+="\t\t<rdf:type rdf:resource=\""+nameSpace+"#"+node+"\" />"+NL;
		return rdf;
	}
	private String closeDescription(){
		String rdf ="";
		rdf+="\t<\\rdf:Description>"+NL;
		return rdf;
	}



	/**
	 

	public void createNeo4jGraphDb()
	{
		clearDb();
		// START SNIPPET: startDb
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
		// END SNIPPET: startDb

		// START SNIPPET: transaction
		try ( Transaction tx = graphDb.beginTx() )
		{

			tx.success();
		}
	}

	private void clearDb()
	{
		try
		{
			FileUtils.deleteRecursively( new File( DB_PATH ) );
		}
		catch ( IOException e )
		{
			throw new RuntimeException( e );
		}
	}



	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook( new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );
	}

	public static void main(String[] args) {
		HashMap<String, String[]> nodesAndProperties = new HashMap<String, String[]>();

		nodesAndProperties.put("Movie", new String[]{"title;string", "year;date"});
		nodesAndProperties.put("Actor", new String[]{"name;string"});
		Neo4j neo = new Neo4j(nodesAndProperties);
		neo.generateRdf("www.neo4j.com","neo");
	}*/
}
