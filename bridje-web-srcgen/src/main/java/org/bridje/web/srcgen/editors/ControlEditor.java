/*
 * Copyright 2017 Bridje Framework.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bridje.web.srcgen.editors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.bridje.jfx.ace.AceEditor;
import org.bridje.jfx.ace.AceMode;
import org.bridje.jfx.utils.JfxUtils;
import org.bridje.web.srcgen.models.ControlDefModel;
import org.bridje.web.srcgen.models.FieldDefModel;
import org.bridje.web.srcgen.models.ResourceRefModel;
import org.bridje.web.srcgen.models.UISuitesModel;

/**
 * Editor for the ControlDefModel class.
 */
public final class ControlEditor extends GridPane
{
    private final SimpleObjectProperty<ControlDefModel> controlProperty = new SimpleObjectProperty<>();

    private final FieldsEditor fieldsEditor = new FieldsEditor();

    private final ListView<ResourceRefModel> resourcesSelector = new ListView<>();

    private final TextField tfName = new TextField();

    private final ComboBox<ControlDefModel> cbBase = new ComboBox<>();

    private final ComboBox<ControlDefModel> cbBaseTemplate = new ComboBox<>();

    private final AceEditor taRender = new AceEditor(AceMode.FTL);

    private ChangeListener<String> nameListener;
    
    private final HBox hbToolbar = new HBox();

    /**
     * Default Constructor
     */
    public ControlEditor()
    {
        setVgap(10);
        setHgap(10);

        setPadding(new Insets(10));

        add(tfName, 0, 0);
        add(cbBase, 1, 0);
        add(cbBaseTemplate, 2, 0);
        add(resourcesSelector, 0, 1, 1, 2);
        add(hbToolbar, 1, 1, 2, 1);
        add(fieldsEditor, 1, 2, 2, 1);
        add(taRender, 0, 3, 3, 1);

        setFillWidth(hbToolbar, true);
        setHgrow(hbToolbar, Priority.ALWAYS);
        setFillWidth(fieldsEditor, true);
        setHgrow(fieldsEditor, Priority.ALWAYS);

        setFillWidth(cbBaseTemplate, true);
        setHgrow(cbBaseTemplate, Priority.ALWAYS);

        setFillWidth(taRender, true);
        setHgrow(taRender, Priority.ALWAYS);
        setFillHeight(taRender, true);
        setVgrow(taRender, Priority.ALWAYS);

        getRowConstraints().add(new RowConstraints());
        getRowConstraints().add(new RowConstraints());
        RowConstraints rowConst = new RowConstraints();
        rowConst.setPercentHeight(45d);
        getRowConstraints().add(rowConst);
        
        hbToolbar.getChildren().add(JfxUtils.createToolButton(UISuitesModel.addField(32), this::addField));
        hbToolbar.getChildren().add(JfxUtils.createToolButton(UISuitesModel.add(32), this::addChild));
        hbToolbar.getChildren().add(JfxUtils.createToolButton(UISuitesModel.delete(32), this::deleteField));

        nameListener = (observable, oldValue, newValue) -> updateName(oldValue, newValue);
        fieldsEditor.setNameListener((observable, oldValue, newValue) -> taRender.searchAndReplace("control." + oldValue, "control." + newValue));
        
        controlProperty.addListener((observable, oldValue, newValue) ->
        {
            if(oldValue != null)
            {
                Bindings.unbindContent(cbBase.getItems(), oldValue.getParent().getControls());
                Bindings.unbindContent(cbBaseTemplate.getItems(), oldValue.getParent().getControlsTemplates());

                oldValue.nameProperty().removeListener(nameListener);
                tfName.textProperty().unbindBidirectional(oldValue.nameProperty());
                Bindings.unbindBidirectional(cbBase.valueProperty(), oldValue.baseProperty());
                Bindings.unbindBidirectional(cbBaseTemplate.valueProperty(), oldValue.baseTemplateProperty());
                fieldsEditor.fieldsProperty().unbindBidirectional(oldValue.fieldsProperty());
                resourcesSelector.itemsProperty().unbindBidirectional(oldValue.resourcesProperty());
                taRender.textProperty().unbindBidirectional(oldValue.renderProperty());
            }
            if(newValue != null)
            {
                if(newValue.getParent().getControls() == null) newValue.getParent().setControls(FXCollections.observableArrayList());
                Bindings.bindContent(cbBase.getItems(), newValue.getParent().getControls());
                if(newValue.getParent().getControlsTemplates() == null) newValue.getParent().setControlsTemplates(FXCollections.observableArrayList());
                Bindings.bindContent(cbBaseTemplate.getItems(), newValue.getParent().getControlsTemplates());

                tfName.textProperty().bindBidirectional(newValue.nameProperty());
                StringConverter<ControlDefModel> contConvert = createStringConverter(name -> findControl(name));
                newValue.baseProperty().bindBidirectional(cbBase.valueProperty(), contConvert);
                StringConverter<ControlDefModel> tmplConverter = createStringConverter(name -> findTemplate(name));
                newValue.baseTemplateProperty().bindBidirectional(cbBaseTemplate.valueProperty(), tmplConverter);
                if(newValue.getFields() == null) newValue.setFields(FXCollections.observableArrayList());
                fieldsEditor.fieldsProperty().bindBidirectional(newValue.fieldsProperty());
                if(newValue.getResources() == null) newValue.setResources(FXCollections.observableArrayList());
                resourcesSelector.itemsProperty().bindBidirectional(newValue.resourcesProperty());
                taRender.textProperty().bindBidirectional(newValue.renderProperty());
                newValue.nameProperty().addListener(nameListener);
            }
            resourcesSelector.setCellFactory(CheckBoxListCell.forListView(ResourceRefModel::selectedProperty));
        });
        
        Menu createMenu = new Menu("Create");
        createMenu.getItems().add(JfxUtils.createMenuItem("Child Field", null, this::createChildField));
        createMenu.getItems().add(JfxUtils.createMenuItem("Children Field", null, this::createChildrenField));
        createMenu.getItems().add(JfxUtils.createMenuItem("Expresion Field", null, this::createExprField));
        createMenu.getItems().add(JfxUtils.createMenuItem("Boolean Field", null, this::createBooleanField));
        taRender.getContextMenu().getItems().add(0, createMenu);
    }

    /**
     * The property for the current control being edited.
     * 
     * @return The control property.
     */
    public SimpleObjectProperty<ControlDefModel> controlsProperty()
    {
        return this.controlProperty;
    }

    /**
     * The the current control being edited.
     * 
     * @return The current control.
     */
    public ControlDefModel getControl()
    {
        return this.controlProperty.get();
    }

    /**
     * The the current control being edited.
     * 
     * @param control The current control.
     */
    public void setControl(ControlDefModel control)
    {
        this.controlProperty.set(control);
    }

    private StringConverter<ControlDefModel> createStringConverter(Callback<String, ControlDefModel> callback)
    {
        return new StringConverter<ControlDefModel>()
        {
            @Override
            public String toString(ControlDefModel object)
            {
                if(object == null) return null;
                return object.getName();
            }

            @Override
            public ControlDefModel fromString(String name)
            {
                return callback.call(name);
            }
        };
    }

    private ControlDefModel findTemplate(String base)
    {
        if(getControl() == null) return null;
        if(getControl().getBaseTemplate() == null) return null;
        return getControl().getParent()
                    .getControlsTemplates()
                    .stream()
                    .filter(c -> c.getName().equals(base))
                    .findFirst()
                    .orElse(null);
    }

    private ControlDefModel findControl(String base)
    {
        if(getControl() == null) return null;
        if(getControl().getBase() == null) return null;
        return getControl().getParent()
                    .getControls()
                    .stream()
                    .filter(c -> c.getName().equals(getControl().getBase()))
                    .findFirst()
                    .orElse(null);
    }

    private void addField(ActionEvent event)
    {
        FieldDefModel field = new FieldDefModel();
        field.setName("newField" + getControl().getFields().size());
        field.setType("String");
        field.setField("outAttr");
        field.setParent(getControl());
        getControl().getFields().add(field);
    }

    private void addChild(ActionEvent event)
    {
        if(fieldsEditor.getSelected() != null)
        {
            if(fieldsEditor.isSelectedParentRoot()
                    && "children".equalsIgnoreCase(fieldsEditor.getSelected().getField()))
            {
                FieldDefModel field = new FieldDefModel();
                field.setField("child");
                field.setName("newChild" + fieldsEditor.getSelected().getChilds().size());
                field.setParent(getControl());
                fieldsEditor.getSelected().getChilds().add(field);
            }
        }
    }

    private void deleteField(ActionEvent event)
    {
        if(fieldsEditor.getSelectedParent() != null && fieldsEditor.getSelected() != null)
        {
            fieldsEditor.getSelectedParent().getChilds().remove(fieldsEditor.getSelected());
        }
    }
    
    private void createChildField(ActionEvent event)
    {
        String selection = taRender.findSelection();
        if(selection != null && !selection.isEmpty())
        {
            ControlDefModel ctrl = new ControlDefModel();
            ctrl.setName("NewControl" + getControl().getParent().getControls().size());
            ctrl.setRender(selection);
            ctrl.setParent(getControl().getParent());
            getControl().getParent().getControls().add(ctrl);

            FieldDefModel field = new FieldDefModel();
            field.setName("newChildField" + getControl().getFields().size());
            field.setType(ctrl.getName());
            field.setField("child");
            field.nameProperty().addListener(fieldsEditor.getNameListener());
            field.setParent(getControl());
            getControl().getFields().add(field);

            taRender.replaceSelection("<#if control." + field.getName() + "??>\n\t<@renderControl control." + field.getName() + " />\n</#if>");
        }
    }

    private void createChildrenField(ActionEvent event)
    {
        String selection = taRender.findSelection();
        if(selection != null && !selection.isEmpty())
        {
            ControlDefModel ctrl = new ControlDefModel();
            ctrl.setName("NewControl" + getControl().getParent().getControls().size());
            ctrl.setRender(selection);
            ctrl.setParent(getControl().getParent());
            getControl().getParent().getControls().add(ctrl);
            FieldDefModel field = new FieldDefModel();
            field.setName("newChildrenField" + getControl().getFields().size());
            field.setField("children");
            field.setParent(ctrl);
            FieldDefModel childField = new FieldDefModel();
            childField.setName(ctrl.getName().toLowerCase());
            childField.setType(ctrl.getName());
            childField.setParent(ctrl);
            field.setChilds(FXCollections.observableArrayList());
            field.getChilds().add(childField);

            field.nameProperty().addListener(fieldsEditor.getNameListener());
            getControl().getFields().add(field);

            taRender.replaceSelection("<#if control." + field.getName() + "??>\n\t<@renderControl control." + field.getName() + " />\n</#if>");
        }
    }

    private void createExprField(ActionEvent event)
    {
        String selection = taRender.findSelection();
        if(selection != null && !selection.isEmpty())
        {
            FieldDefModel field = new FieldDefModel();
            field.setName("newExprAttr" + getControl().getFields().size());
            field.setType("String");
            field.setField("outAttr");
            field.setDefaultValue("\"" + selection + "\"");
            field.nameProperty().addListener(fieldsEditor.getNameListener());
            field.setParent(getControl());
            getControl().getFields().add(field);

            taRender.replaceSelection("${control." + field.getName() + "}");
        }
    }

    private void createBooleanField(ActionEvent event)
    {
        String selection = taRender.findSelection();
        if(selection != null && !selection.isEmpty())
        {
            FieldDefModel field = new FieldDefModel();
            field.setName("newBoolAttr" + getControl().getFields().size());
            field.setType("Boolean");
            field.setField("outAttr");
            field.setDefaultValue("false");
            field.nameProperty().addListener(fieldsEditor.getNameListener());
            field.setParent(getControl());
            getControl().getFields().add(field);

            taRender.replaceSelection("<#if control." + field.getName() + "?? && control." + field.getName() + ">" + selection + "</#if>");
        }
    }

    private void updateName(String oldValue, String newValue)
    {
        if(getControl() == null) return;
        getControl().getParent().getControls().forEach(c -> updateChildTypes(c, oldValue, newValue));
    }

    private void updateChildTypes(ControlDefModel control, String oldValue, String newValue)
    {
        if(control.getFields() == null) return;
        control.getFields()
                .stream()
                .filter(f -> "child".equalsIgnoreCase(f.getField()))
                .filter(f -> oldValue.equals(f.getType()))
                .forEach(f -> f.setType(newValue));

        control.getFields()
                .stream()
                .filter(f -> "children".equalsIgnoreCase(f.getField()))
                .forEach(f -> updateChildrenTypes(f, oldValue, newValue));
    }

    private void updateChildrenTypes(FieldDefModel field, String oldValue, String newValue)
    {
        if(field.getChilds() == null) return;
        field.getChilds()
                .stream()
                .filter(f -> oldValue.equals(f.getType()))
                .forEach(f -> f.setType(newValue));
    }
}
