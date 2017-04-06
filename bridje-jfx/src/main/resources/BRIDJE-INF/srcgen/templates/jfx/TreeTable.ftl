<#include "./ObjectUtils.ftl" />

package ${object.package};

<#list model.includes![] as inc>
import ${inc.fullName};
</#list>
<#list object.includes![] as inc>
import ${inc.fullName};
</#list>
import javafx.beans.property.*;
import java.util.Objects;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class ${object.name}TreeTable extends TreeTableView<${object.name}>
{
    <#list object.allProperties as property>
    private TreeTableColumn<${object.name}, ${property.javaType}> ${property.name}Column;

    </#list>
    <#list object.allProperties as property>
    public TreeTableColumn<${object.name}, ${property.javaType}> add${property.name?cap_first}Column(String title)
    {
        if(title != null)  get${property.name?cap_first}Column().setText(title);
        getColumns().add(get${property.name?cap_first}Column());
        return get${property.name?cap_first}Column();
    }

    public void editable${property.name?cap_first}Column(Callback<TreeTableColumn<${object.name}, ${property.javaType}>, TreeTableCell<${object.name}, ${property.javaType}>> editor, EventHandler<CellEditEvent<${object.name}, ${property.javaType}>> eventHandler)
    {
        get${property.name?cap_first}Column().setEditable(editor != null);
        if(editor != null)
        {
            this.setEditable(true);
            get${property.name?cap_first}Column().setCellFactory(editor);
            get${property.name?cap_first}Column().setOnEditCommit(event -> 
            {
                event.getRowValue().getValue().set${property.name?cap_first}(event.getNewValue());
                if(eventHandler != null) eventHandler.handle(event);
            });
        }
    }

    public void editable${property.name?cap_first}Column(<#if property.javaType != "String">StringConverter<${property.javaType}> converter, </#if>EventHandler<CellEditEvent<${object.name}, ${property.javaType}>> eventHandler)
    {
        <#if property.javaType == "String">
        editable${property.name?cap_first}Column(TextFieldTreeTableCell.forTreeTableColumn(), eventHandler);
        <#else>
        Callback<TreeTableColumn<${object.name}, ${property.javaType}>, TreeTableCell<${object.name}, ${property.javaType}>> editor = null;
        if(converter != null) editor = TextFieldTreeTableCell.forTreeTableColumn(converter);
        editable${property.name?cap_first}Column(editor, eventHandler);
        </#if>
    }

    public TreeTableColumn<${object.name}, ${property.javaType}> get${property.name?cap_first}Column()
    {
        if(${property.name}Column == null)
        {
            ${property.name}Column = new TreeTableColumn<>("${object.name?cap_first}");
            ${property.name}Column.setCellValueFactory(param -> param.getValue().getValue().${property.name}Property()<#if property.convertProp??>.${property.convertProp}()</#if>);
        }
        return ${property.name}Column;
    }

    </#list>
}