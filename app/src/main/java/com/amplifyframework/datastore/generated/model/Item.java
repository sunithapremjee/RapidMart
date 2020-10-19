package com.amplifyframework.datastore.generated.model;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Item type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Items")
public final class Item implements Model {
  public static final QueryField ID = field("id");
  public static final QueryField USER = field("user");
  public static final QueryField TITLE = field("title");
  public static final QueryField DESCRIPTION = field("description");
  public static final QueryField PDFDOC = field("pdfdoc");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String user;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String pdfdoc;
  public String getId() {
      return id;
  }
  
  public String getUser() {
      return user;
  }
  
  public String getTitle() {
      return title;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getPdfdoc() {
      return pdfdoc;
  }
  
  private Item(String id, String user, String title, String description, String pdfdoc) {
    this.id = id;
    this.user = user;
    this.title = title;
    this.description = description;
    this.pdfdoc = pdfdoc;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Item item = (Item) obj;
      return ObjectsCompat.equals(getId(), item.getId()) &&
              ObjectsCompat.equals(getUser(), item.getUser()) &&
              ObjectsCompat.equals(getTitle(), item.getTitle()) &&
              ObjectsCompat.equals(getDescription(), item.getDescription()) &&
              ObjectsCompat.equals(getPdfdoc(), item.getPdfdoc());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUser())
      .append(getTitle())
      .append(getDescription())
      .append(getPdfdoc())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Item {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("user=" + String.valueOf(getUser()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("pdfdoc=" + String.valueOf(getPdfdoc()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Item justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Item(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      user,
      title,
      description,
      pdfdoc);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    Item build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep user(String user);
    BuildStep description(String description);
    BuildStep pdfdoc(String pdfdoc);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private String user;
    private String description;
    private String pdfdoc;
    @Override
     public Item build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Item(
          id,
          user,
          title,
          description,
          pdfdoc);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep user(String user) {
        this.user = user;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep pdfdoc(String pdfdoc) {
        this.pdfdoc = pdfdoc;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String user, String title, String description, String pdfdoc) {
      super.id(id);
      super.title(title)
        .user(user)
        .description(description)
        .pdfdoc(pdfdoc);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder user(String user) {
      return (CopyOfBuilder) super.user(user);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder pdfdoc(String pdfdoc) {
      return (CopyOfBuilder) super.pdfdoc(pdfdoc);
    }
  }
  
}
