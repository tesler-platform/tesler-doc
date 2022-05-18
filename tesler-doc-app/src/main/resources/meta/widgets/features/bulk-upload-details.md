
Okay, it seems that our widget have some action called "File Upload" to show the popup. Actions are defined by
 [getActions](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/service/ResponseService.java#L143)
method in service implementation, so let's check it out:

##### getActions
```java
@Override
public Actions<BulkInsertExampleDTO> getActions() {
    return Actions.<BulkInsertExampleDTO>builder()
        .action("file-upload", "File Upload")
            .scope(ActionScope.BC).add()
        .action("file-upload-save", "File Upload Save")
            .scope(ActionScope.BC).invoker(this::fileUpload).add()
        .delete().add()
        .build();
}
```

Aha, we have three actions here!  
`delete` action is pretty regular and we'll skip it for now.  
`file-upload` is a built-in action that will open popup with file uploader. The scope for this actions is assigned as
business component (`ActionScope.BC`), which means that this action will be called over the entire widget rather than
a specific record.  
`file-upload-save` is also a built-in action, but with a customizable implementation: as you can see, we provide some
invoker function `this::fileUpload` that will be called when the user confirms his upload by clicking a save button
on the popup.  

So how does our custom invoker is implemented?  

##### Custom invoker

```java
private ActionResultDTO<BulkInsertExampleDTO> fileUpload(BusinessComponent bc, BulkInsertExampleDTO data) {
    // We'll receive ids of uploaded files
    Optional.ofNullable(data.getBulkIds()).ifPresent(res -> {
        res.forEach(item -> {
            // We'll create a new record for each file id we've received 
            BulkInsertExample itemResult = new BulkInsertExample();
            // We'll map every file id into actual file entity
            Long id = Long.parseLong(item);
            FileEntity file = jpaDao.findById(FileEntity.class, id);
            // ...and assign this entity to our new record
            itemResult.setFileEntity(file);
            // We'll also modify this new record somehow, e.g. we'll generate `name` property:
            itemResult.setName("Result " + UUID.randomUUID().toString().substring(0, 7));
            // And then we'll save created record
            baseDAO.save(itemResult);
        });
    });
    // We'll return result of the action here; any post action goes here
    return new ActionResultDTO<BulkInsertExampleDTO>();
}
```

So the overall idea is that we receive an array of affected records as an input (in our case it's an array of ids of
uploaded files), and then implement some custom logic over this array, e.g. we create new records from files array.  
For input array we use a `bulkIds` field in our DTO declaration:
```java
public class BulkInsertExampleDTO extends DataResponseDTO {
    
    // ...

	private List<String> bulkIds;
    
    // ...
}
```

The upload is performed by Tesler UI multipart post request to [FileController#upload](https://github.com/tesler-platform/tesler/blob/master/tesler-core/src/main/java/io/tesler/core/controller/FileController.java#L74)
the same way as `fileUpload` fields.  
