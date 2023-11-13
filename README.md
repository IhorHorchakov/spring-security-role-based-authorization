This is a demo project to set up and play with role-based authorization using Spring Security

### Theory
_Authorization_ is the process of giving the user permission to access a specific resource. Giving someone permission to
download a particular file on a server or providing individual users with administrative access to an application are 
good examples of authorization.

In secure environments, authorization must always follow authentication. Users should first prove that their identities 
are genuine before an organization’s administrators grant them access to the requested resources. For example, after a 
file server authenticates a user, it can check which files or directories that can be read, written, or deleted. 
This is where authorization comes into play.

![authentication-and-authorization](https://github.com/IhorHorchakov/spring-security-role-based-authorization/blob/master/img/authentication-and-authorization.png?raw=true)

User authorization helps to control and secure access to sensitive databases, private and personal data, and corporate 
resources. Organizations typically implement a granular authorization structure that does not grant flat access to all 
resources. Instead, different roles are assigned access to the resources relevant to their job. This access control 
practice is called **the least privilege principle**, and it guides many organizations in protecting their resources
from unauthorized access.

##### Types of authorization
- Discretionary Access Control (DAC) – DAC determines permissions depending on the specific user and their access groups. 
A DAC model allows every object in a system to be accessed by a particular group or identity. Those in charge of granting
authorization can provide admin permission to other users.
- Mandatory Access Control (MAC) – MAC determines authorization of entities at the operating system level. MAC commonly 
governs permissions for threads and processes, defining which files and memory objects they can access.
- Role-Based Access Control (RBAC) – RBAC is used to enforce access controls defined in the DAC or MAC model. RBAC builds
on predefined roles and permissions, assigns users to roles, and configures a system so that only specific roles can access
each object.
- Attribute-based Access Control (ABAC) – ABAC is used to enforce access controls in a policy-based manner. It uses 
attributes, which can be attached to a user, a resource, an object, or an entire environment. An entity is authorized
if the authentication system finds that all the attributes defined in the policy are true.

### Spring Security: using RBAC authorization
The role-based access control implies using roles in combination with their permissions. We will use 'ROLE_USER' that has
permission 'CAN_VIEW', and 'ROLE_MANAGER' that has permissions 'CAN_VIEW', 'CAN_DELETE_RESOURCE'.
This approach is much better suited to be used in applications because it enables developers to configure **granular**
access control. We can mix and match roles and permissions as granular as necessary.

##### AuthorizeHttpRequestsConfigurer
Spring Framework uses the approach of _configurers_ - an ability to expand Spring configuration by adding custom components. 
`AuthorizeHttpRequestsConfigurer` enables authorization feature in application by adding `AuthorizationFilter` to the filter
chain : 

![](https://github.com/IhorHorchakov/spring-security-role-based-authorization/blob/master/img/filter-chain.png?raw=true)

`AuthorizeHttpRequestsConfigurer` is getting created by using _.authorizeHttpRequests()_ method in SecurityConfig.

##### AuthorizationManager
`AuthorizationFilter` is the entry point of authorization process for the HTTP requests. This filter utilizes _AuthorizationManager_ to delegate 
verification process and relies on its decision. _AuthorizationManager_ has many implementations to verify the authority
in a various ways: RequestMatcherDelegatingAuthorizationManager, AuthenticatedAuthorizationManager, DeferringObservationAuthorizationManager, PreAuthorizeAuthorizationManager.

![](https://github.com/IhorHorchakov/spring-security-role-based-authorization/blob/master/img/authorization-manager.png?raw=true)

##### Method security
Spring Security supports authorization semantics at the method level. We could secure our web layer and service layer by
restricting which roles are able to execute a particular method — and test it using dedicated method-level security test support.

To enable method security we use `@EnableGlobalMethodSecurity` annotation. This annotation has properties we could override:
- _prePostEnabled_ property enables Spring Security pre/post annotations
- _securedEnabled_ property determines if the @Secured annotation should be enabled
- _jsr250Enabled_ property allows us to use the @RoleAllowed annotation

In this project we use `@PreAuthorized` annotation to check user authorities. Both `@PreAuthorize` and `@PostAuthorize` 
annotations provide expression-based access control. So, predicates can be written using SpEL (Spring Expression Language).
The `@PreAuthorize` annotation checks the given expression before entering the method, whereas the `@PostAuthorize` annotation
verifies it after the execution of the method and could check the returned result as well.

All the possible built-In expressions to check access control are [listed here](https://docs.spring.io/spring-security/site/docs/4.1.x/reference/html/el-access.html).
SpEL provides a flexible way to check the access control - we can do referring to beans and even check path variables.

`PreAuthorizeAuthorizationManager` runs the SpEL to verify an expression in @PreAuthorize when the target method is matched
and going to execute. This manager returns ExpressionAuthorizationDecision back to AuthorizationFilter, so we could debug
it if there is a need to check how @PreAuthorize works.

-------
Useful links:
https://frontegg.com/blog/authentication-vs-authorization

https://www.baeldung.com/spring-security-method-security

https://www.baeldung.com/role-and-privilege-for-spring-security-registration

https://docs.spring.io/spring-security/site/docs/4.1.x/reference/html/el-access.html

