insert into responsibilities (id, resp_type, screens, dept_id, internal_role_cd) VALUES (nextval('RESPONSIBILITY_ID'), 'SCREEN',
'[
    {
      "name": "getting-started",
      "text": "Getting Started",
      "icon": "rocket",
      "url": "/screen/getting-started",
      "defaultScreen": true
    },
    {
      "name": "features",
      "text": "Features",
      "icon": "star",
      "url": "/screen/features"
    },
    {
      "name": "components",
      "text": "Components Overview",
      "icon": "block",
      "url": "/screen/components"
    },
    {
      "name": "tutorial",
      "text": "Tutorial",
      "icon": "book",
      "url": "/screen/tutorial"
    },
    {
      "name": "api-reference",
      "text": "API Reference",
      "icon": "api",
      "url": "/screen/api-reference"
    },
    {
      "id": "id3",
      "name": "example",
      "text": "Example",
      "url": "/screen/example"
    }
    ]', 0 , 'ADMIN');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'bankcard');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'banklist');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'form');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'hello');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'teslerinfo');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'list');
insert into responsibilities (id, resp_type, dept_id, internal_role_cd, responsibilities) VALUES (nextval('RESPONSIBILITY_ID'), 'VIEW', 0, 'ADMIN', 'controls');
