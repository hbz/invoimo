<%@ page import="org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title><g:layoutTitle default="${meta(name: 'app.name')}"/></title>
    <meta name="description" content="">
    <meta name="author" content="">

    <meta name="viewport" content="initial-scale = 1.0">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <tmpl:/layouts/favicon />

    <g:layoutHead/>
    <r:layoutResources/>

	<tmpl:/layouts/analytics />
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <sec:ifLoggedIn>
            <a class="brand" href="${createLink(uri: '/')}">${message(code:'laser', default:'LAS:eR')}</a>
          </sec:ifLoggedIn>
          <div class="nav-collapse">
            <ul class="nav">
              <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Data Explorer <b class="caret"></b> </a>
                <ul class="dropdown-menu" style="max-width:none;">
                  <li<%= request.forwardURI == "${createLink(uri: '/')}" ? ' class="active"' : '' %>><a href="${createLink(uri: '/')}">Home</a></li>
                  <li <%='package'== controllerName ? ' class="active"' : '' %>><g:link controller="package">Package</g:link></li>
                  <li <%='org'== controllerName ? ' class="active"' : '' %>><g:link controller="org">Organisations</g:link></li>
                  <li <%='platform'== controllerName ? ' class="active"' : '' %>><g:link controller="platform">Platform</g:link></li>
                  <li <%='titleInstance'== controllerName ? ' class="active"' : '' %>><g:link controller="titleInstance">Title Instance</g:link></li>
                  <li <%='titleInstancePackagePlatform'== controllerName ? ' class="active"' : '' %>><g:link controller="titleInstancePackagePlatform">Title Instance Package Platform</g:link></li>
                  <li <%='subscription'== controllerName ? ' class="active"' : '' %>><g:link controller="subscription">Subscriptions</g:link></li>
                  <li <%='license'== controllerName ? ' class="active"' : '' %>><g:link controller="license">Licenses</g:link></li>
                </ul>
              </li>

              <g:if test="${user}">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Forms and Actions <b class="caret"></b> </a>
                  <ul class="dropdown-menu" style="max-width:none;">

                    <li <%= ( ( 'myInstitutions'== controllerName ) && ( 'manageAffiliations'==actionName ) ) ? ' class="active"' : '' %>>
                       <g:link controller="myInstitutions" action="manageAffiliations">Manage Affiliations</g:link></li>

                     <li class="divider"></li>
                     <g:if test="${user.affiliations && user.affiliations.size() > 0}">
                       <g:each in="${user.affiliations}" var="ua">
                         <li>
                           <g:link controller="myInstitutions" 
                                   action="licenses" 
                                   params="${[shortcode:ua.org.shortcode]}">${ua.org.name} - Licenses</g:link>
                         </li>
                        <li>
                           <g:link controller="myInstitutions" 
                                   action="currentSubscriptions" 
                                   params="${[shortcode:ua.org.shortcode]}">${ua.org.name} - Current Subscriptions</g:link>
                         </li>
                        <li>
                           <g:link controller="myInstitutions" 
                                   action="addSubscription" 
                                   params="${[shortcode:ua.org.shortcode]}">${ua.org.name} - Add Subscriptions</g:link>
                         </li>
                       </g:each>
                     </g:if>
                     <g:else>
                       <li>Please use the manage affiliations option to request access
                                to your home institution</li>
                     </g:else>
                  </ul>
                </li>
              </g:if>

              <sec:ifAnyGranted roles="ROLE_ADMIN">
                 <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown"> Admin Actions <b class="caret"></b> </a>
                  <ul class="dropdown-menu">
                    <li <%= ( ( 'admin'== controllerName ) && ( 'manageAffiliationRequests'==actionName ) ) ? ' class="active"' : '' %>>
                       <g:link controller="admin" action="manageAffiliationRequests">Manage Affiliation Requests</g:link></li>
                    <li class="divider"></li>
                    <li <%= ( ( 'admin'== controllerName ) && ( 'reconcile'==actionName ) ) ? ' class="active"' : '' %>>
                       <g:link controller="admin" action="reconcile">Manage Data Reconciliation</g:link>
                    </li>
                    <li <%= ( ( 'startFTIndex'== controllerName ) && ( 'index'==actionName ) ) ? ' class="active"' : '' %>>
                       <g:link controller="startFTIndex" action="index">Start FT Index Update</g:link>
                  </ul>
                </li>

              </sec:ifAnyGranted>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <div class="navbar-push"></div>

    <div class="container-fluid">
      <g:layoutBody/>
    </div>

    <div id="Footer">
        <div class="container">
            <div class="row">
                <div class="pull-right">
                    <a href="http://www.kbplus.ac.uk"><div class="sprite sprite-kbplus_logo">Knowledge Base Plus</div></a>
                </div>
            </div>
        </div>
    </div>

    <div class="support-tab">
        <a href="mailto:TODO"><i class="icon-question-sign icon-white"></i>Support Queries</a>
    </div>
            
  <r:layoutResources/>

  </body>
</html>
