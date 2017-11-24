<#ftl encoding="UTF-8">
<#-- 
 # Copyright 2016 Bridje Framework.
 #
 # Licensed under the Apache License, Version 2.0 (the "License");
 # you may not use this file except in compliance with the License.
 # You may obtain a copy of the License at
 #
 #      http://www.apache.org/licenses/LICENSE-2.0
 #
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS,
 # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 # See the License for the specific language governing permissions and
 # limitations under the License.
-->

<#macro renderStyle theme style>
    <#if style?starts_with("http://") || style?starts_with("https://")>
        <link rel="stylesheet" href="${style}" />
    <#else>
        <link rel="stylesheet" href="/__themes/${theme}/${style}" />
    </#if>
</#macro>

<#macro renderLink theme rel href>
    <#if href?starts_with("http://") || href?starts_with("https://")>
        <link rel="${rel!}" href="${href}" />
    <#else>
        <link rel="${rel!}" href="/__themes/${theme}/${href}" />
    </#if>
</#macro>

<#macro renderScript theme script async=false defer=false>
    <#if script?starts_with("http://") || script?starts_with("https://")>
        <script src="${script}" type="text/javascript"<#if async> async</#if><#if defer> defer</#if>></script>
    <#else>
        <script src="/__themes/${theme}/${script}" type="text/javascript"<#if async> async</#if><#if defer> defer</#if>></script>
    </#if>
</#macro>

<#macro renderThemeScripts themeName>
</#macro>

<#macro renderThemeStyles themeName>
</#macro>

<#macro renderControl control>
    macro renderControl must be implemented for this theme.
</#macro>

<#macro renderAllControls controls>
    <#list controls as w>
        <@renderControl w />
    </#list>
</#macro>

<#macro renderMetaTag>
    <#list view.metaTags![] as meta>
        <meta name="${meta.name!}" content="${meta.content!}">
    </#list>
</#macro>

<#macro renderViewContainer>
    <div id="bridje-view-container" >
        <#nested />
    </div>
</#macro>

<#macro renderBody>
    <body>
        <#nested />
    </body>
</#macro>

<#macro renderHead>
    <head>
        <title>${view.title!}</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#nested />
    </head>
</#macro>

<#macro renderFullView themeName>
    <!DOCTYPE html>
    <html>
        <@renderHead>
            <@renderMetaTag />
            <@renderThemeStyles themeName />
        </@renderHead>
        <@renderBody>
            <@renderViewContainer>
                <#if view.root??>
                    <@renderPartialView view.root />
                </#if>
            </@renderViewContainer>

            <@renderThemeScripts themeName />
        </@renderBody>
    </html>
</#macro>

<#macro renderPartialView currentControl>
    <input type="hidden" name="__view" value="${view.name}" />
    <input type="hidden" name="__action" value="" />
    <@renderState />
    <@renderControl currentControl />
</#macro>

<#macro renderState>
    <#assign currState = stateProvider.currentState />
    <#list currState?keys as k>
        <input type="hidden" name="__state.${k}" value="${currState[k]}" />
    </#list>
</#macro>

<#macro renderMain themeName >
    <#if result??
         && result.data??
         && result.data.class.simpleName == "RedirectTo"
         && result.data.status??
         && result.data.resource??>
        <script>
            window.location = '${result.data.resource}';
        </script>
    <#else>
        <#if control??>
            <@renderPartialView control />
        <#else>
            <@renderFullView themeName />
        </#if>
    </#if>
</#macro>
