# Heroku Deployment Checklist

## ✅ Pre-Deployment Checklist

### 1. Application Ready
- [ ] Application builds successfully (`mvn clean package`)
- [ ] All tests pass (if any)
- [ ] Application runs locally
- [ ] Database connection works
- [ ] Email functionality works

### 2. Heroku Account Setup
- [ ] Heroku account created
- [ ] Heroku CLI installed
- [ ] Logged into Heroku CLI (`heroku login`)

### 3. Git Repository
- [ ] Project is in a Git repository
- [ ] All changes committed
- [ ] Repository is clean

## 🚀 Deployment Steps

### Step 1: Create Heroku App
```bash
heroku create your-app-name
```

### Step 2: Add PostgreSQL Database
```bash
heroku addons:create heroku-postgresql:mini
```

### Step 3: Add SendGrid for Email
```bash
heroku addons:create sendgrid:starter
```

### Step 4: Configure Environment Variables
```bash
# Email Configuration
heroku config:set MAIL_HOST=smtp.sendgrid.net
heroku config:set MAIL_PORT=587
heroku config:set MAIL_USERNAME=apikey
heroku config:set MAIL_PASSWORD=your-sendgrid-api-key

# App URL
heroku config:set APP_URL=https://your-app-name.herokuapp.com
```

### Step 5: Deploy
```bash
git add .
git commit -m "Deploy to Heroku"
git push heroku main
```

### Step 6: Scale Dynos
```bash
heroku ps:scale web=1
```

## ✅ Post-Deployment Checklist

### 1. Application Health
- [ ] App starts successfully
- [ ] No errors in logs (`heroku logs --tail`)
- [ ] Database connection established
- [ ] Tables created automatically

### 2. Feature Testing
- [ ] Home page loads
- [ ] User registration works
- [ ] Email verification works
- [ ] Login/logout works
- [ ] Service ordering works
- [ ] Profile page works
- [ ] Language toggle works

### 3. Security & Performance
- [ ] HTTPS enabled (automatic on Heroku)
- [ ] CSRF protection enabled
- [ ] Environment variables secure
- [ ] No sensitive data in logs

### 4. Monitoring
- [ ] Logs accessible
- [ ] Performance monitoring set up
- [ ] Error tracking configured

## 🔧 Troubleshooting

### Common Issues

1. **Build Failures**
   - Check `pom.xml` for missing dependencies
   - Verify Java version compatibility
   - Check for compilation errors

2. **Database Issues**
   - Verify PostgreSQL add-on is active
   - Check database credentials
   - Ensure JPA dialect is correct

3. **Email Issues**
   - Verify SendGrid configuration
   - Check API key validity
   - Test email sending manually

4. **Runtime Issues**
   - Check application logs
   - Verify environment variables
   - Test locally with production config

## 📊 Monitoring Commands

```bash
# View real-time logs
heroku logs --tail

# Check app status
heroku ps

# View configuration
heroku config

# Restart application
heroku restart

# Access database
heroku pg:psql
```

## 💰 Cost Management

- **Dynos**: Start with 1 basic dyno ($7/month)
- **Database**: Mini PostgreSQL ($5/month)
- **Email**: SendGrid starter (free for low volume)
- **Total**: ~$12/month for basic setup

## 🔒 Security Best Practices

1. **Environment Variables**: Never commit secrets
2. **Database**: Use Heroku's managed PostgreSQL
3. **HTTPS**: Automatically provided by Heroku
4. **Logs**: Monitor for security issues
5. **Updates**: Keep dependencies updated

## 📈 Scaling Considerations

- **Traffic**: Monitor dyno usage
- **Database**: Upgrade PostgreSQL plan if needed
- **Email**: Upgrade SendGrid plan for higher volume
- **CDN**: Consider CloudFlare for static assets

## 🆘 Support Resources

- [Heroku Documentation](https://devcenter.heroku.com/)
- [Spring Boot on Heroku](https://devcenter.heroku.com/articles/deploying-spring-boot-apps-on-heroku)
- [PostgreSQL on Heroku](https://devcenter.heroku.com/articles/heroku-postgresql)
- [SendGrid Documentation](https://sendgrid.com/docs/) 