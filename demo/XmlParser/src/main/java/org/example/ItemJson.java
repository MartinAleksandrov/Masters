package org.example;

@Jsonable
public class ItemJson {
    @JsonField
    private  String name = "Book";

    @JsonField(title = "sub_title")
    private  String subTitle = "Science";

    @JsonField(expectedType = JsonFieldType.PLAIN)
    private  double price = 12.0;
}
