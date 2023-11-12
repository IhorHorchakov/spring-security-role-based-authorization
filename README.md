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
- Discretionary Access Control (DAC) – DAC determines privileges depending on the specific user and their access groups. 
A DAC model allows every object in a system to be accessed by a particular group or identity. Those in charge of granting
authorization can provide admin permission to other users.
- Mandatory Access Control (MAC) – MAC determines authorization of entities at the operating system level. MAC commonly 
governs permissions for threads and processes, defining which files and memory objects they can access.
- Role-Based Access Control (RBAC) – RBAC is used to enforce access controls defined in the DAC or MAC model. RBAC builds
on predefined roles and privileges, assigns users to roles, and configures a system so that only specific roles can access
each object.
- Attribute-based Access Control (ABAC) – ABAC is used to enforce access controls in a policy-based manner. It uses 
attributes, which can be attached to a user, a resource, an object, or an entire environment. An entity is authorized
if the authentication system finds that all the attributes defined in the policy are true.

### Spring Security: Using RBAC authorization



-------
Useful links:
https://frontegg.com/blog/authentication-vs-authorization