package Json;

import com.alibaba.fastjson.JSON;
import util.Result;

public class JsonConverter {  //json格式转换器  7
    public static   String convert(Result result){ //传入需要转换的json格式

        return JSON.toJSONString(result); //传出去json格式

    }
}
