package com.yff.ecbackend.common.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

public class Object6Serialize extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (Objects.nonNull(value)) {
            //保留2位小数#代表末位是0舍去
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            //四舍五入
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            String result = decimalFormat.format(value);
            //注意这里不能使用jsonGenerator.writeNumber(result);方法，不然又会把.00去掉
            jsonGenerator.writeString(result);
        } else {
            jsonGenerator.writeString("0.00");
        }
    }
}
