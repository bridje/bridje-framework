
package ${model.package};

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.bridje.ioc.Ioc;
import org.bridje.ioc.Inject;
import org.bridje.ioc.thls.Thls;
import org.bridje.sql.*;
import org.bridje.orm.*;
import javax.annotation.Generated;

/**
 * This class represents the ${model.name} data model.
 * it must be use to read and write ${model.name} entities.
 * The full list of ${model.name} entities is the following.
 * <ul>
<#list model.entities as entity >
 * <li>${entity.name}<br></li>
</#list>
 * </ul>
 */
@Generated(value = "org.bridje.orm.srcgen.OrmSourceGenerator", date = "${.now?string("yyyy-MM-dd")}", comments = "Generated by Bridje ORM API")
public class ${model.name}Base
{
    public static final Schema SCHEMA;

    static final Set<Class<?>> ENTITIES;

    static final Map<Class<?>, EntityValueFinder> TO_KEYS;

    static final Map<Class<?>, EntityValueFinder> TO_ENTITIES;

    static {
        SCHEMA = SQL.buildSchema("${model.schema}")
                    <#list model.entities as entity>
                    .table(${entity.name}.TABLE)
                    </#list>
                    <#list model.entities as entity>
                    <#list entity.foreignKeys![] as key>
                    <#if key.isWithItSelf || key.fkOnModel>
                    .foreignKey(SQL.buildForeignKey(${entity.name}.TABLE, ${entity.name}.${key.column?upper_case})
                                    .references(${key.with.name}.TABLE)
                                    .strategy(ForeignKeyStrategy.${key.onUpdate}, ForeignKeyStrategy.${key.onDelete})
                                    .build())
                    </#if>
                    </#list>
                    </#list>
                    .build();

        ENTITIES = new HashSet<>();
        <#list model.entities as entity>
        ENTITIES.add(${entity.name}.class);
        </#list>

        TO_KEYS = new HashMap<>();
        <#list model.entities as entity>
        TO_KEYS.put(${entity.name}.class, (v) -> ((${entity.name})v).get${entity.key.name?cap_first}());
        </#list>

        TO_ENTITIES = new HashMap<>();
        <#list model.entities as entity>
        TO_ENTITIES.put(${entity.name}.class, (v) -> get().find${entity.name}(((${entity.key.type.javaType})v)));
        </#list>
        
    }

    public static ${model.name} get()
    {
        return Thls.get(ORMEnvironment.class).getModel(${model.name}.class);
    }

    @Inject
    protected EntityContext ctx;

    @Inject
    protected SQLEnvironment env;

    public void fixSchema() throws SQLException
    {
        env.fixSchema(SCHEMA);
    }

    protected <T> T find(Object key, Class<T> entityCls) throws SQLException
    {
        EntityValueFinder finder = TO_ENTITIES.get(entityCls);
        if(finder == null) return null;
        return (T)finder.find(key);
    }

    protected <T> Object getKey(T entity) throws SQLException
    {
        EntityValueFinder finder = TO_KEYS.get(entity.getClass());
        if(finder == null) return null;
        return finder.find(entity);
    }

    <#list model.entities as entity>
    protected ${entity.name} parse${entity.name}(SQLResultSet rs) throws SQLException
    {
        ${entity.name} entity = new ${entity.name}();
        parse${entity.name}(entity, rs);
        return entity;
    }

    protected void parse${entity.name}(${entity.name} entity, SQLResultSet rs) throws SQLException
    {
        entity.set${entity.key.name?cap_first}(rs.get(${entity.name}.${entity.key.column?upper_case}));
        ctx.put(entity.get${entity.key.name?cap_first}(), entity);
        <#list entity.fields as field>
        entity.set${field.name?cap_first}(rs.get(${entity.name}.${field.column?upper_case}<#if field.with??>, (key) -> find${field.with.name}(${entity.key.type.parserCode("key")})</#if>));
        </#list>
    }

    public ${entity.name} find${entity.name}(${entity.key.type.javaType} key) throws SQLException
    {
        if(ctx.contains(${entity.name}.class, key)) return ctx.get(${entity.name}.class, key);
        return env.fetchOne(${entity.name}.FIND_QUERY, this::parse${entity.name}, key);
    }

    <#if entity.key.autoIncrement>
    protected void doSave${entity.name}(${entity.name} entity) throws SQLException
    {
        if(entity.get${entity.key.name?cap_first}() == null)
        {
            entity.set${entity.key.name?cap_first}(env.fetchOne(${entity.name}.INSERT_QUERY, (rs) -> rs.get(${entity.name}.${entity.key.column?upper_case}), <#list entity.nonAiFields as field>entity.get${field.name?cap_first}()<#sep>, </#sep></#list>));
        }
        else
        {
            env.update(${entity.name}.UPDATE_QUERY, <#list entity.nonAiFields as field>entity.get${field.name?cap_first}()<#sep>, </#sep></#list>, entity.get${entity.key.name?cap_first}());
        }
    }

    <#else>
    protected void doInsert${entity.name}(${entity.name} entity) throws SQLException
    {
        <#if entity.key.autoIncrement>
        entity.set${entity.key.name?cap_first}(env.fetchOne(${entity.name}.INSERT_QUERY, (rs) -> rs.get(${entity.name}.${entity.key.column?upper_case}), <#list entity.nonAiFields as field>entity.get${field.name?cap_first}()<#sep>, </#sep></#list>));
        <#else>
        env.update(${entity.name}.INSERT_QUERY, <#list entity.nonAiFields as field>entity.get${field.name?cap_first}()<#sep>, </#sep></#list>);
        </#if>
    }

    protected void doUpdate${entity.name}(${entity.name} entity) throws SQLException
    {
        doUpdate${entity.name}(entity, entity.get${entity.key.name?cap_first}());
    }

    protected void doUpdate${entity.name}(${entity.name} entity, ${entity.key.type.javaType} key) throws SQLException
    {
        env.update(${entity.name}.UPDATE_QUERY, <#list entity.nonAiFields as field>entity.get${field.name?cap_first}()<#sep>, </#sep></#list>, key);
    }

    </#if>
    protected void doDelete${entity.name}(${entity.name} entity) throws SQLException
    {
        doDelete${entity.name}(entity.get${entity.key.name?cap_first}());
    }

    protected void doDelete${entity.name}(${entity.key.type.javaType} key) throws SQLException
    {
        env.update(${entity.name}.DELETE_QUERY, key);
    }

    <#macro renderConditionContent entity condition >
        <@compress single_line=true><#compress>
        <#if condition.content??>
        <#list condition.content as newCond>
            .${newCond.booleanOperator}(<@renderCondition entity newCond />)
        </#list>
        </#if>
        </#compress></@compress>
    </#macro>
    <#macro renderCondition entity condition >
        <@compress single_line=true><#compress>
        ${entity.name}.${condition.field.column?upper_case}.${condition.operatorMethod}(${condition.value})<#if condition.not>.not()</#if>
        <@renderConditionContent entity condition />
        </#compress></@compress>
    </#macro>
    <#list entity.queries as query>
    <#if query.queryType == "select">
    public List<<#if query.fetchField??>${query.fetchField.type.javaType}<#else>${entity.name}</#if>> ${query.name}<@compress single_line=true><#compress>
                                    (
                                        <#if query.withPaging>
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p}, 
                                        </#list>
                                        </#if>
                                        Paging paging
                                        <#else>
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p}<#sep>, </#sep>
                                        </#list>
                                        </#if>
                                        </#if>
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.select(${entity.name}.TABLE.getColumns())
                        .from(${entity.name}.TABLE)
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        <#if query.withPaging>
                        .limit(paging.toLimit())
                        </#if>
                        .toQuery();
        <#if query.fetchField??>
        return env.fetchAll(query, (rs) -> rs.get(${entity.name}.${query.fetchField.column?upper_case}<#if query.fetchField.with??>, (key) -> find${query.fetchField.with.name}(${entity.key.type.parserCode("key")})</#if>));
        <#else>
        return env.fetchAll(query, this::parse${entity.name});
        </#if>
    }

    <#if query.withPaging>
    public Paging ${query.name}Paging<@compress single_line=true><#compress>
                                    (
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p},
                                        </#list>
                                        </#if>
                                        int pageSize
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.select(SQL.count())
                        .from(${entity.name}.TABLE)
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        .toQuery();
        return Paging.of(env.fetchOne(query, (rs) -> rs.get(SQL.count())), pageSize);
    }

    </#if>
    </#if>
    <#if query.queryType == "count">
    public int ${query.name}<@compress single_line=true><#compress>
                                    (
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p}<#sep>, </#sep>
                                        </#list>
                                        </#if>
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.select(SQL.count())
                        .from(${entity.name}.TABLE)
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        .toQuery();
        return env.fetchOne(query, (rs) -> rs.get(SQL.count()));
    }

    </#if>
    <#if query.queryType == "selectOne">
    public <#if query.fetchField??>${query.fetchField.type.javaType}<#else>${entity.name}</#if> ${query.name}<@compress single_line=true><#compress>
                                    (
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p}<#sep>, </#sep>
                                        </#list>
                                        </#if>
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.select(${entity.name}.TABLE.getColumns())
                        .from(${entity.name}.TABLE)
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        .toQuery();
        <#if query.fetchField??>
        return env.fetchOne(query, (rs) -> rs.get(${entity.name}.${query.fetchField.column?upper_case}<#if query.fetchField.with??>, (key) -> find${query.fetchField.with.name}(${entity.key.type.parserCode("key")})</#if>));
        <#else>
        return env.fetchOne(query, this::parse${entity.name});
        </#if>
    }

    </#if>
    <#if query.queryType == "update">
    public int ${query.name}<@compress single_line=true><#compress>
                                    (
                                        <#list query.params?keys as p>
                                        ${query.params[p].type.javaType} ${p}<#sep>, </#sep>
                                        </#list>
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.update(${entity.name}.TABLE)
                        <#list query.sets as set>
                        .set(${entity.name}.${set.field.column?upper_case}, ${set.value})
                        </#list>
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        .toQuery();
        return env.update(query);
    }

    </#if>
    <#if query.queryType == "delete">
    public int ${query.name}<@compress single_line=true><#compress>
                                    (
                                        <#if query.where??>
                                        <#list query.where.params?keys as p>
                                        ${query.where.params[p].type.javaType} ${p}<#sep>, </#sep>
                                        </#list>
                                        </#if>
                                    ) throws SQLException</#compress></@compress>
    {
        Query query = SQL.delete()
                        .from(${entity.name}.TABLE)
                        <#if query.where??>
                        <@compress single_line=true><#compress>.where(
                            <@renderCondition entity query.where />
                        )</#compress></@compress>
                        </#if>
                        .toQuery();
        return env.update(query);
    }

    </#if>
    </#list>
    </#list>
}
