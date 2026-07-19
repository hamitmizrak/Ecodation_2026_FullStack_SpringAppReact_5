// src/routes/router.jsx
// Eski ProtectedRoute, AdminRoute, WriterRoute, DefaultUser, hata ve basit sayfa
// dosyaları bu dosyada birleştirilmiştir.

import React, { useEffect, useState } from 'react';
import { Navigate, Outlet, Route, Routes, useLocation } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

import ProjectHeader from '../pages/ProjectHeader';
import ProjectFooter from '../pages/ProjectFooter';
import ProjectMain from '../pages/ProjectMain';

import { AdminLayout, AdminHome } from '../areas/admin/AdminShell';
import BlogCategory from '../areas/admin/BlogCategory';
import About from '../areas/admin/About';
import Blog from '../areas/admin/Blog';
import BlogApi from '../areas/writer/BlogApi';

import {
  initFromStorage,
  logout,
  selectAuth,
} from '../features/auth/authSlice';
import { extractApiData, fetchMe, resolveImageUrl } from '../core/api';

const normalizeRoles = (roles = []) =>
  roles
    .map((role) => String(role).toUpperCase().trim())
    .map((role) => role.replace(/^ROLE_/, ''));

export function ProtectedRoute({ roles = [] }) {
  const dispatch = useDispatch();
  const location = useLocation();
  const { isAuthenticated, roles: currentRoles, loading, token } = useSelector(selectAuth);

  useEffect(() => {
    dispatch(initFromStorage());
  }, [dispatch]);

  const storedToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

  if (loading && (token || storedToken)) {
    return <div className="container py-5">Yükleniyor...</div>;
  }

  if (!isAuthenticated && !storedToken) {
    return <Navigate to="/" replace state={{ from: location }} />;
  }

  if (roles.length) {
    const have = normalizeRoles(currentRoles);
    const need = normalizeRoles(roles);
    if (!have.some((role) => need.includes(role))) {
      return <Navigate to="/403" replace />;
    }
  }

  return <Outlet />;
}

export function AdminRoute() {
  return <ProtectedRoute roles={['ADMIN']} />;
}

export function WriterRoute() {
  return <ProtectedRoute roles={['WRITER', 'ADMIN']} />;
}

export function DefaultUserRoute() {
  return <ProtectedRoute roles={['USER', 'ADMIN']} />;
}

export function Forbidden403() {
  return (
    <div className="container py-5">
      <h1 className="display-5">403 - Yetkisiz</h1>
      <p>Bu sayfaya erişim yetkiniz bulunmuyor.</p>
    </div>
  );
}

export function NotFound404() {
  return (
    <div className="container py-5">
      <h1 className="display-5">404 - Sayfa Bulunamadı</h1>
      <p>Aradığınız sayfa bulunamadı.</p>
    </div>
  );
}

export function HomePage() {
  return <ProjectMain />;
}

// Önceden ayrı olan Dashboard özelliği, ihtiyaç halinde named export olarak korunmuştur.
export function Dashboard() {
  const dispatch = useDispatch();
  const auth = useSelector(selectAuth);
  const [me, setMe] = useState(auth.user || null);

  useEffect(() => {
    let active = true;
    fetchMe()
      .then((response) => {
        if (active) setMe(extractApiData(response));
      })
      .catch(() => {});
    return () => {
      active = false;
    };
  }, []);

  const imageUrl = resolveImageUrl(me?.imageUrl || me?.image);

  return (
    <div className="container" style={{ maxWidth: 720, margin: '32px auto' }}>
      <h3>Dashboard</h3>
      <p>Hoş geldin{me?.registerName ? `, ${me.registerName}` : ''} 👋</p>
      {imageUrl && <img src={imageUrl} alt="avatar" style={{ maxWidth: 160 }} />}
      <div className="mt-3">
        <button className="btn btn-outline-secondary" onClick={() => dispatch(logout())}>
          Çıkış Yap
        </button>
      </div>
    </div>
  );
}

function PublicLayout() {
  return (
    <>
      <ProjectHeader logo="fa-solid fa-blog" />
      <div className="container">
        <Outlet />
      </div>
      <ProjectFooter copy="&copy; Bütün Haklar Saklıdır." />
    </>
  );
}

export default function Router() {
  return (
    <Routes>
      <Route element={<PublicLayout />}>
        <Route path="/" element={<ProjectMain />} />
        <Route path="/index" element={<ProjectMain />} />
        <Route path="/403" element={<Forbidden403 />} />
        <Route path="/404" element={<NotFound404 />} />

        <Route element={<ProtectedRoute />}>
          {/* Mevcut projede yorumda olan dashboard özelliği burada korunur. */}
          {/* <Route path="/dashboard" element={<Dashboard />} /> */}
        </Route>

        <Route element={<WriterRoute />}>
          <Route path="/writer/blog-api" element={<BlogApi />} />
        </Route>

        {/* Eski davranış korunur: bilinmeyen adres anasayfaya döner. */}
        <Route path="*" element={<Navigate to="/" replace />} />
      </Route>

      <Route element={<AdminRoute />}>
        <Route path="/admin" element={<AdminLayout />}>
          <Route index element={<AdminHome />} />
          <Route path="blog-category" element={<BlogCategory />} />
          <Route path="blog" element={<Blog />} />
          <Route path="about" element={<About />} />
        </Route>
      </Route>
    </Routes>
  );
}
