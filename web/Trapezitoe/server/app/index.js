const express = require("express");
const app = express();
const cookieParser = require('cookie-parser');
const crypto = require('crypto');
const fs = require('fs');

const sessions = new Map();

function getRandomToken() {
	return crypto.randomBytes(48).toString('base64');
}

app.use(cookieParser())
app.use((req, res, next) => {
	var session;
    if (!req.cookies.session) {
		session = getRandomToken();
        res.cookie("session", session);
    } else {
		session = req.cookies.session;
	}

	var data;
	if (sessions.has(session)) {
		data = sessions.get(session);
	} else {
		data = {you: 100, chloe: 800, achilles: 600};
		
		sessions.set(session, data);
	}
	res.locals.data = data;

    next();
});
app.use(express.urlencoded({ extended: false }));

app.use(express.static('static'));

app.get("/balance", (req, res) => {
    res.send(res.locals.data);
});

app.post("/transfer", (req, res) => {
	target = req.body.name;
	amount = parseInt(req.body.amount);

	console.log("Transfer to " + target + " of " + amount);

	if (!res.locals.data.hasOwnProperty(target)) {
		res.status(400).send("Unknown target");
		return;
	}

	if (amount > res.locals.data.you) {
		res.status(400).send("Not enough balance");
		return;
	}

	res.locals.data.you -= amount;
	res.locals.data[target] += amount;

	res.redirect("/")
});

app.post("/purchase", (req, res) => {
	if (res.locals.data.you < 500) {
		res.status(400).send("Not enough balance");
		return;
	}

	res.locals.data.you -= 500;

	fs.readFile('flag.txt', 'utf8', (err, data) => {
		if (err) {
			res.status(500);
			console.error(err);
			return;
		}

		res.send(data);
	});
});

app.post("/reset", (req, res) => {
	res.clearCookie("session");
	res.redirect("/")
});

app.listen(80, () => {
    console.log("App listening on port 80");
});
