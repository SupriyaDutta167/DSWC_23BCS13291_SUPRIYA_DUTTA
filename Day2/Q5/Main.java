package Day2.Q5;

abstract class DataPayload{
    public abstract String getRawContent();
}

class JsonPayload extends DataPayload{
    private String content;

    public JsonPayload(String content){
        this.content= content;
    }

    @Override
    public String getRawContent(){
        return content;
    }
}

class XmlPayload extends DataPayload{
    private String content;

    public XmlPayload(String content){
        this.content= content;
    }

    @Override
    public String getRawContent(){
        return content;
    }
}

class PipelineProcessor<T extends DataPayload>{
    public void process(T payload){
        System.out.println("Processing Payload:");
        System.out.println(payload.getRawContent());
    }
}
public class Main {
    public static void main(String[] args){
        JsonPayload json= new JsonPayload("{\"name\":\"Supriya\"}");
        XmlPayload xml= new XmlPayload("<name> Supriya</name>");

        PipelineProcessor<JsonPayload>jsonProcessor= new PipelineProcessor<>();
        PipelineProcessor<XmlPayload>xmlProcessor= new PipelineProcessor<>();

        jsonProcessor.process(json);
        xmlProcessor.process(xml);
    }
}
