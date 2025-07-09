USE book_manager;

INSERT INTO book
VALUES (100, 'Kotlin入門', 'コトリン太郎', '1950-10-01'),
       (200, 'Java入門', 'ジャヴァ太郎', '2005-08-29');

INSERT INTO user
VALUES (1, 'admin@test.com', '$argon2id$v=19$m=19456,t=2,p=1$cm1tbjFaamo4bVkwakFSdA$eScxnOTB+ihEYPTkjmQjG8+r3a8ZzuPUH9HfN0+nSKY', '管理者', 'ADMIN'),
       (2, 'user@test.com', '$argon2id$v=19$m=19456,t=2,p=1$Z1BRTHlYUXM1VjhkL2I3NA$8WVM46eAPhN91ilZidtTvQi6Mv0Dadx9XZK4702HgDw', 'ユーザー', 'USER');

# VALUES (1, 'admin@test.com', '', '管理者', 'ADMIN'),
#        (2, 'user@test.com', '', 'ユーザー', 'USER');

/*3個目のハッシュ値はパスワード情報が入っていて
外部からアクセスされたときに悪用されないようにしている
ubuntu に argon2 をインストールしてハッシュ値を生成する*/

/* (-n : 改行を防ぐ)
echo -n 'admin@pass' | argon2 $(openssl rand -base64 12) -id -t 2 -k 19456
Type:		Argon2id
Iterations:	2
Memory:		19456 KiB
Parallelism:	1
Hash:		7927319ce4c1fa284460f4e48e64231bcfabddaf19cee3d41fd1df374fa748a6
Encoded:	$argon2id$v=19$m=19456,t=2,p=1$cm1tbjFaamo4bVkwakFSdA$eScxnOTB+ihEYPTkjmQjG8+r3a8ZzuPUH9HfN0+nSKY
0.158 seconds
Verification ok
*/