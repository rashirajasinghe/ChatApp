# Chat App System

A full-stack chat app built with React, Node.js, Express.js, and MongoDB. Features Google OAuth 2.0 authentication, CRUD operations for chats, and PDF report generation.

## Features

### Authentication
- Google OAuth 2.0 integration
- Secure session management
- Protected routes and middleware

### Chat Management
- **CRUD Operations**: Create, Read, Update, Delete tasks
- **Task Fields**: Title, Description, Deadline, Assigned To, Status
- **Search & Filter**: Search by title, description, or assignee
- **Sorting**: Sort by date, title, deadline, or status
- **Status Management**: Pending, In Progress, Completed, Cancelled

### Reporting
- PDF report generation using jsPDF
- Downloadable task reports with all task details

### UI/UX
- Modern, responsive design with Tailwind CSS
- Dashboard with task statistics
- Intuitive chat management interface
- Mobile-friendly layout

## Tech Stack

### Frontend
- React 18
- Vite
- Tailwind CSS
- React Router DOM
- Axios
- React Hot Toast
- Lucide React (Icons)

### Backend
- Node.js
- Express.js
- MongoDB with Mongoose
- Passport.js (Google OAuth 2.0)
- Express Session
- jsPDF (PDF generation)
- CORS

## Project Structure

```
task-management-system/
├── backend/
│   ├── models/
│   │   ├── Task.js
│   │   └── User.js
│   ├── controllers/
│   │   ├── authController.js
│   │   └── taskController.js
│   ├── routes/
│   │   ├── auth.js
│   │   └── tasks.js
│   ├── middlewares/
│   │   └── auth.js
│   ├── server.js
│   ├── package.json
│   └── env.example
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Layout.jsx
│   │   │   └── TaskModal.jsx
│   │   ├── contexts/
│   │   │   └── AuthContext.jsx
│   │   ├── pages/
│   │   │   ├── Login.jsx
│   │   │   ├── Dashboard.jsx
│   │   │   └── TaskManager.jsx
│   │   ├── App.jsx
│   │   ├── main.jsx
│   │   └── index.css
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── index.html
└── README.md
```

## Setup Instructions

### Prerequisites
- Node.js (v16 or higher)
- MongoDB (local or MongoDB Atlas)
- Google Cloud Console account for OAuth setup

### Backend Setup

1. Navigate to the backend directory:
   ```bash
   cd backend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create environment file:
   ```bash
   cp env.example .env
   ```

4. Configure environment variables in `.env`:
   ```
   PORT=5000
   MONGODB_URI=mongodb://localhost:27017/taskmanagement
   GOOGLE_CLIENT_ID=your_google_client_id_here
   GOOGLE_CLIENT_SECRET=your_google_client_secret_here
   SESSION_SECRET=your_session_secret_here
   CLIENT_URL=http://localhost:3000
   ```

5. Start MongoDB (if running locally):
   ```bash
   mongod
   ```

6. Start the backend server:
   ```bash
   npm run dev
   ```

### Frontend Setup

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm run dev
   ```

### Google OAuth Setup

1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project or select an existing one
3. Enable the Google+ API
4. Create OAuth 2.0 credentials
5. Add authorized redirect URIs:
   - `http://localhost:5000/api/auth/google/callback` (for development)
6. Copy the Client ID and Client Secret to your `.env` file

## Usage

1. Open your browser and navigate to `http://localhost:3000`
2. Click "Sign in with Google" to authenticate
3. Once logged in, you'll see the dashboard with task statistics
4. Navigate to "Chat Manager" to create, edit, and manage chats
5. Use the search and filter options to find specific tasks
6. Download PDF reports from the dashboard

## API Endpoints

### Authentication
- `GET /api/auth/google` - Initiate Google OAuth
- `GET /api/auth/google/callback` - Google OAuth callback
- `GET /api/auth/me` - Get current user
- `POST /api/auth/logout` - Logout user

### Tasks
- `GET /api/tasks` - Get all tasks (with search, filter, sort)
- `GET /api/tasks/:id` - Get single task
- `POST /api/tasks` - Create new task
- `PUT /api/tasks/:id` - Update task
- `DELETE /api/tasks/:id` - Delete task
- `GET /api/tasks/report/pdf` - Download PDF report

## Development

### Running in Development Mode
```bash
# Backend (Terminal 1)
cd backend
npm run dev

# Frontend (Terminal 2)
cd frontend
npm run dev
```

### Building for Production
```bash
# Frontend
cd frontend
npm run build

# Backend
cd backend
npm start
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Commit with meaningful messages
5. Push to your fork
6. Create a Pull Request

## License

This project is licensed under the MIT License.

## Deployment

The application can be deployed to platforms like:
- Heroku (Backend)
- Vercel/Netlify (Frontend)
- MongoDB Atlas (Database)

Make sure to update environment variables and CORS settings for production deployment.
