var mysql = require('mysql');
var express = require('express');
var bodyParser = require('body-parser');
var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.listen(3000, '0.0.0.0', function () {
	console.log('서버 실행 중...');
});

app.post('/user/join', function (req, res) {
	console.log(req.body);
	var kakaoId = req.body.kakaoId;
	var userName = req.body.userName;

	if(userName == null) userName = "null";

	var sql = 'INSERT INTO Users (kakao_id, user_name) VALUES (?, ?)';
	var params = [kakaoId, userName];

	connection.query(sql, params, function (err, result) {
		var resultCode = 404;
		var message = '에러가 발생했습니다';

		if(err) {
			console.log(err);
		} else {
			resultCode = 200;
			message = '회원가입에 성공했습니다.';
		}

		res.json({
			'code': resultCode,
			'message': message
		});
	});
});

app.post('/reply', function(req, res) {
	console.log(req.body)
	var replyId = req.body.replyId;

	var sql = 'UPDATE Reply SET is_shared = 1 WHERE reply_id = ?';
	var params = [replyId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '공유 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '공유 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		});
	})
})

app.post('/post/reply', function(req, res) {
	console.log(req.body);
	var diaryId = req.body.diaryId;
	var userFromId = req.body.userFromId;
	var userToId = req.body.userToId;
	var replyDate = req.body.replyDate;
	var content = req.body.content;
	var isShared = 0;
	var isChecked = 0;

	var sql = 'INSERT INTO Reply(diary_id, user_from_id, user_to_id, reply_date, content, is_shared, is_checked) VALUES(?, ?, ?, ?, ?, ?, ?)';
	var params = [diaryId, userFromId, userToId, replyDate, content, isShared, isChecked];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '답장 보내기 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '답장 보내기 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		});
	})
})

app.put('/diary', function(req, res) {
	console.log(req.body);
	var diaryId = req.body.diaryId;
	var content = req.body.content;
	var date = req.body.date;

	var sql = 'UPDATE Diary SET content = ? WHERE diary_id = ?';
	params = [content, diaryId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '일기 수정 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '일기 수정 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		});
	})
})

app.put('/reply/check', function(req, res) {
	console.log(req.body);
	var diaryId = req.body.diaryId;

	var sql = 'UPDATE Reply SET is_checked = 1 WHERE diary_id = ?';
	params = [diaryId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '알림 확인 업데이트 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '알림 확인 업데이트 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		});
	})
})

app.get('/diary/list', function (req, res) {
	console.log(req.query);
	var userId = req.query.userId;
	var year = req.query.date.substr(0, 4);
	var month = req.query.date.substr(5, 2);

	var sql = 'SELECT * FROM Diary WHERE year(diary_date) = ? and month(diary_date) = ? and user_id = ?';
	var params = [year, month, userId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;

		if(err) console.log(err);
		else {
			resultCode = 200;
			//console.log(result);
		}
		res.json({
			'result': result
		});
	});

});

app.get('/wero/list', function(req, res) {
	console.log(req.query);

	var sql = 'SELECT reply_id, user_from_id, user_name, content, heart FROM Reply, Users' 
		+ ' WHERE Reply.user_from_id = Users.kakao_id AND is_shared = 1';
	var params = [];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;

		if(err) console.log(err);
		else {
			resultCode = 200;
			console.log(result);
		}
		res.json({
			'result': result
		});
	});
})

app.get('/diary', function (req, res) {
	console.log(req.query);
	var diaryId = req.query.diaryId;

	var sql = 'SELECT * FROM Diary WHERE diary_id = ?'
	var params = [diaryId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;

		if(err) console.log(err);
		else {
			resultCode = 200;
			console.log(result);
		}
		res.json({
			'result': result
		});
	});

});

app.get('/reply', function (req, res) {
	console.log(req.query);
	var diaryId = req.query.diaryId;

	 var sql = 'SELECT * FROM Reply WHERE diary_id = ?';
	var params = [diaryId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;

		if(err) console.log(err);
		else {
			resultCode = 200;
			console.log(result);
		}
		res.json({
			'result': result
		});
	});

});

app.get('/post/list', function (req, res) {
	console.log(req.query);
	var userId = req.query.userId;

	var sql = 'SELECT * FROM Post natural join Diary natural join Users WHERE user_to_id = ? and kakao_id = ?';
	params = [userId, userId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '편지 목록 불러오기 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '편지 목록 불러오기 성공';
			console.log(message);
		}

		res.json({'result': result});
	})
})

app.get('/reply/list', function(req, res) {
	console.log(req.query);
	var userId = req.query.userId;

	var sql = 'SELECT reply_id, Diary.diary_id, user_from_id, user_to_id, reply_date, Diary.content as content, Reply.content as reply'
		+ ' FROM Reply, Diary WHERE user_to_id = ? AND Reply.diary_id = Diary.diary_id AND Reply.is_checked = 0;'
	params = [userId, userId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '답장 리스트 불러오기 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '답장 리스트 불러오기 성공';
			console.log(message);
		}

		console.log(result);
		res.json({
			'result': result
		});
	})

})

app.get('/name', function(req, res) {
	console.log(req.query);
	console.log(req.query);
	var userId = req.query.userId;

	var sql = 'SELECT user_name FROM Users WHERE kakao_id = ?'
	params = [userId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = '닉네임 불러오기 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = '닉네임 불러오기 성공';
			console.log(message);
		}

		console.log(result);
		res.json({
			'result': result
		});
	})

})

app.delete('/post', function(req, res) {
	console.log(req.query);
	var diaryId = req.query.diaryId;
	var userToId = req.query.userToId;

	var sql = 'DELETE FROM Post WHERE diary_id = ? and user_to_id = ?';
	var params = [diaryId, userToId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = 'Post 삭제 실패';

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = 'Post 삭제 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		})
	})
})

app.delete('/diary', function(req, res) {
	console.log(req.query);
	var diaryId = req.query.diaryId;

	var sql1 = 'DELETE FROM Post WHERE diary_id = ?;';
	var sql2 = 'DELETE FROM Diary WHERE diary_id = ?;';
	sql1 = mysql.format(sql1, diaryId);
	sql2 = mysql.format(sql2, diaryId);

	connection.query(sql1 + sql2, function(err, result) {
		var resultCode = 404;
		var message = "삭제 실패";

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = "삭제 성공";
		}

		res.json({
			'code': resultCode,
			'message': message
		})
	})
})

app.delete('/reply', function(req, res) {
	console.log(req.query);
	var replyId = req.query.replyId;

	var sql = 'DELETE FROM Reply WHERE reply_id = ?;';
	var params = [replyId];

	connection.query(sql, params, function(err, result) {
		var resultCode = 404;
		var message = "삭제 실패";

		if(err) console.log(err);
		else {
			resultCode = 200;
			message = "삭제 성공";
		}

		res.json({
			'code': resultCode,
			'message': message
		})
	})
})

//res.json 빼먹으면 timeout
app.post('/diary/save', function (req, res) {
	console.log(req.body);
	var userId = req.body.userId;
	var date = req.body.date;
	var content = req.body.content;
	var isShared = req.body.isShared;

	var sql = 'INSERT INTO Diary (user_id, diary_date, content, is_shared) VALUES (?, ?, ?, ?)';
	var params = [userId, date, content, isShared];

	connection.query(sql, params, function (err, result) {
		var resultCode = 404;
		var message = '일기 저장 실패';

		if(err) {
			console.log(err);
		} else {
			resultCode = 200;
			message = '일기 저장 성공';
			console.log(message);
		}

		res.json({
			'code': resultCode,
			'message': message
		})
	});
	
});

//callback hell... express async-await
app.post('/post', async (req, res) => {
	console.log(req.body);
	var userId = req.body.userId;

	const diaryId = await getDiaryId(userId);
	console.log(diaryId);
	const randomUsers = await getRamdomUsers(userId);
	console.log(randomUsers);

	console.log('T___T');
	const message = await insertPost(userId, diaryId, randomUsers);

	res.json({
		message: message
	})

});

function getDiaryId(userId) {
	return new Promise(resolve => {
		var sql = 'SELECT diary_id FROM Diary WHERE user_id = ? ORDER BY diary_id DESC limit 1';
		var params = [userId]
	
		connection.query(sql, params, function(err, result) {
			if(err) {
				console.log('error in getDiaryId');
				console.log(err);
			}
			else {
				return setTimeout(() => {resolve(result[0].diary_id)}, 100)
			}
		})
	})
}

function getRamdomUsers(userId) {
	return new Promise(resolve => {
		var sql = 'select kakao_id from Users order by rand() limit 2';
		var randomUser = [];
		connection.query(sql, function(err, result) {
			if(err) {
				console.log('error in getRandomUsers');
				console.log(err);
			}
			else {
				for(var i = 0; i < 2; i++) {
					if(result[i].kakao_id == userId) continue;
					randomUser.push(result[i].kakao_id);
				}				
				return setTimeout(() => {resolve(randomUser)}, 100)
			}
		})
	})
}

function insertPost(userId, diaryId, randomUsers) {
	return new Promise(resolve => {
		var sql = 'INSERT INTO Post(user_from_id, user_to_id, diary_id) VALUES (?, ?, ?)';
		var message;
		for (var i = 0; i < randomUsers.length; i++) {
			var params = [userId, randomUsers[i], diaryId];
			connection.query(sql, params, function (err, result) {
				var resultCode = 404;
				message = '편지 보내기 실패';

				if (err) {
					console.log(message);
					console.log(err);
				}
				else {
					resultCode = 200;
					message = '편지 전송 성공';
					console.log(message);
				}
			});
		}
		return message;
	})
	
}
