# customer-events

Customer Events Project

customer events: http://3.15.40.28:8081/

## API List
| API	| Type	| Url					| Controller|
| ----	| ---	| --					| ----------|
| Customer Activate | POST| /v1/customer/activate|  MemberController|
| web enabled| POST|  /v1/customer/webenabled| CustomerController|
| Decryption| GET| /api/v1/decryption?memberid=fiX6YZYO7js=| MemberController|
| Encryption| GET| /api/v1/decryption?memberid=fiX6YZYO7js=| MemberController|
| Create update Profile| PUT| /v1/customer/104| CustomerController|

## Steps to Run an application
1. Take the latest copy of the code from the repository
2. Open project.
3. build the project using Gradle-><<Project>>->Tasks->build->build.
4. Once Project get build successfully, press Shift+F10 to run it.

// SQL
INSERT INTO domain_details (site_id, customer_id, site_code,template_code,site_name)
VALUES (007, 133, '301','bbss','TestSelva');
INSERT INTO domain_details (site_id, customer_id, site_code,template_code,site_name)
VALUES (008, 133, '301','bbss','TestSelva');

select * from domain_details where customer_id = 133

select * from customer_user where email_id='selvaganeshan_s@yahoo.com'
delete from customer_user where customer_user.last_name = 'Sakthivel'