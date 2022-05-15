package 生产者;

import com.alibaba.fastjson.JSON;
import util.Result;

class JsonZH {  //json格式转换器  7
    public static String convert(Result result) { //传入需要转换的json格式

        return JSON.toJSONString(result); //传出去json格式

    }
}
