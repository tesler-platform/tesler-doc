## Filter types

The system has the ability to filter for entries by value in the field. To do this, you must indicate that this field is allowed to filter in FieldMetaBuilder, and also indicate in the DTO the @SearchParameter annotation for the field that is being searched.

Filter available column types:
* number
* input
* date
* dateTime
* dateTimeWithSeconds
* dictionary
* text
* percent
* money
* checkbox
* pickList


For the listed types of widget fields, it is possible to add a filter with the necessary filtering condition, which is specified by the filter type in the field `type: "equals"`.

Supported Filter Operations:

| Type | Description
|:---|:---|
| **equals** | equal values
| **greaterThan** | values are greater than specified
| **lessThan** | values are less than specified
| **greaterOrEqualThan** | values are greater than or equal to the specified
| **lessOrEqualThan** | values are less than or equal to the specified
| **contains** | values containing specified
| **specified** | certain values
| **specifiedBooleanSql** | certain boolean values for SQL widgets
| **equalsOneOf** | equal to one of these
| **containsOneOf** | includes one of these


