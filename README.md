# webper / 2020 ECONOVATION 웹프로젝트

![logo](https://user-images.githubusercontent.com/48192106/81941010-38489200-9633-11ea-8ae3-1f1463904aed.png)

#### 관심있는 웹사이트를 drag & drop으로 간편하게 모아 보관할 수 있는 웹서비스

website scraper / 웹 퍼가요~

> 팀원

- backend : 배종진

- frontend : 최진영

- frontend : 김서영

### Repositories

![repositories](https://user-images.githubusercontent.com/48192106/81939758-b4da7100-9631-11ea-9ea4-a2c73e8cb3e1.png)

- upstream : econovation에 만들, 우리가 공유할 repository
- origin : fork를 통해 자신 계정에 생성한 repository

### Branches
- feature별로 브랜치 이름 정하기 : [feature/기능요약] 형식을 추천 ex) feature/login
- 큰 카테고리는 카테고리 브랜치를 생성하고 그 밑의 하위 브랜치로 생성해서 작업하기
  ex) feature/navbar 브랜치에서 create-new-category 브랜치 생성
- 한 feature를 여러명이 작업할 때는 브랜치 이름 끝에 이름을 붙여 local과 origin에서 작업하고, upstream의 feature브랜치에 pull request 날리기

### Commit Message Convention

- feat : 새로운 기능 추가
- fix : 버그 수정
- docs : 문서 수정
- style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
- refactor : 코드 리펙토링
- test : 테스트 코드, 리펙토링 테스트 코드 추가
- chore : 빌드 업무 수정, 패키지 매니저 수정

### Ticket 처리하기

> 하나의 티켓은 되도록 하나의 커밋으로 한다
>
> 기능 구현 전에 여러 개의 티켓으로 작업을 나누고 시작한다

예시로 navbar 관련 기능 중에 '새로운 카테고리 생성'이라는 기능 티켓을 처리한다고 가정

1. upstream의 feature/navbar 브랜치에서 create-new-category 브랜치 생성

```shell
(feature/navbar) git fetch upstream feature/navbar
(feature/navbar) git checkout -b create-new-category
```

2. 작업 브랜치에서 변경사항 커밋

```shell
(create-new-category) git commit -m "feat: new category 생성기능 완성"
```

3. 만약 커밋이 불필요하게 여러 개로 나뉘어져 있다면 squash를 한다 (아래는 커밋 2개를 합치는 예시)

```shell
(create-new-category) git rebase -i HEAD~2
```

4. 작업 브랜치를 upstream/feature-user에 rebase한다

```shell
(create-new-category) git pull --rebase upstream feature-user
```

5. 작업 브랜치를 origin에 push한다

```shell
(create-new-category) git push origin create-new-category
```

6. GitHub에서 create-new-category 브랜치를 feature/navbar에 merge하는 Pull Request를 생성한다

7. 같은 feature를 개발하는 동료에게 리뷰 승인을 받은 후 자신의 Pull Request를 merge한다
8. 기능을 다 구현해서 쓸모 없어진 브랜치는 삭제를 한다

```
(create-new-category) git checkout develop
(develop) git push origin --delete create-new-category // origin에 있는 브랜치를 삭제할 경우
(develop) git push remote --delete create-new-category // upstream에 있는 브랜치를 삭제할 경우
(develop) git branch -d create-new-category // local에 있는 브랜치를 삭제할 경우
```

### 한 기능을 여러 사람이 함께 구현할때

feature/login기능을 choi와 kim이 함께 구현한다면 feature/login 브랜치 안에서 각자의 브랜치를 따와 작업하고 origin에 올린 다음 upstream의 feature branch에 Pull Request를 통해 merge한다

```shell
(develop) git checkout -b feature/login
(feature/login) git checkout -b login_layout_kim
// ... 작업
(login_layout_kim) git add *
(login_layout_kim) git commit -m "feat: login layout 구현"
(login_layout_kim) git push origin login_layout_kim
```

GitHub에서 origin/login_layout_kim => upstream/feature/login 으로 Pull Request를 생성, 리뷰 및 검토 후 Merge한다. 마찬가지로 다 사용한 브랜치는 삭제해준다