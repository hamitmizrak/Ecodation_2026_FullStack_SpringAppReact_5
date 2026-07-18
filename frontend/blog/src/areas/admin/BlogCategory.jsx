// BlogCategory.jsx (Final)
// Backend'e uyumlu: categoryId, categoryName, systemCreatedDate alanları
// Görsel alanları kaldırıldı (backend tarafında yok)
// Tabloda filtreleme + sıralama + sayfalama var
// Modal yönetimi; ESC ile kapanır; body scroll kilitlenir
// rfce ==> TAB

// React
import React, { useEffect, useState, useMemo } from 'react';

// API
import { API_BASE, ENDPOINTS } from '../../config/api';
import { showSuccess, showError } from './resuability/toastHelper'; // varsa kullan; yoksa console.log ile değiştir
import axios from 'axios'; // react + spring boot backend için axios kullanıyoruz

// -------- Helpers --------
const extractData = (res) => {
  const temp = res?.data;
  return temp?.data ?? temp?.result ?? temp?.items ?? temp?.content ?? temp ?? [];
};

// format Date (+3)
const fmtDate = (iso) =>
  !iso ? '' : new Date(iso).toLocaleString('tr-TR', { timeZone: 'Europe/Istanbul' });

function GlobalBackdrop({ show, onClose }) {
  if (!show) return null;
  return (
    <div
      className="modal-backdrop fade show"
      style={{ zIndex: 1040 }}
      onClick={onClose || undefined}
    />
  );

  /////////////////////////////////////////////////////////////////////////////////
  // Blog Category
  function BlogCategory() {
    // State
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(false);

    // Modals
    const [showCreate, setShowCreate] = useState(false);
    const [showEdit, setShowEdit] = useState(false);
    const [showView, setShowView] = useState(false);
    const [showDelete, setShowDelete] = useState(false);

    // Modal
    const anyOpen = showCreate || showEdit || showView || showDelete;

    // Selection + Form
    const [selected, setSelected] = useState(null);
    const [form, setForm] = useState({ categoryName: '' });
    const [formError, setformError] = useState({});

    // Filter + Sort + Pagination
    const [query, setQuery] = useState('');
    const [sortKey, setSortKey] = useState('categoryId'); // categoryId | categoryName | systemCreatedDate
    const [sortDir, setSortDir] = useState('asc'); // asc | desc
    const [page, setPage] = useState(1); // 1.sayfa
    const [pageSize, setPageSize] = useState(10); // Veri listesinden sayfada 10 tane gelsin

    // useEffect (anyOpen)
    // useEffect(()=>{},[])
    useEffect(() => {
      if (anyOpen) {
        document.body.classList.add('modal-open');
      } else {
        document.body.classList.remove('modal-open');
      }
    }, [anyOpen]);

    // useEffect (Modal)
    useEffect(() => {
      const onKey = (e) => {
        if (e.key !== 'Escape') return;
        if (showCreate) return closeCreate();
        if (showEdit) return closeEdit();
        if (showView) return closeView();
        if (showDelete) return closeDelete();
      };
      window.addEventListener('keydown', onKey);
      return () => window.removeEventListener('keydown', onKey);
    }, [showCreate, showEdit, showView, showDelete]);

    // Liste (Api)
    const fetchList = async () => {
      setLoading(true);
      try {
        const res = await axios.get(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.LIST}`);
        const data = extractData(res);
        const arr = Array.isArray(data) ? data : Array.isArray(data?.content) ? data.content : [];
        setItems(arr);
      } catch (e) {
        showError?.('Blog Kategori listesi yüklenemedi') ?? console.error(e);
      } finally {
        setLoading(false);
      }
    }; // end fetchList

    // useEffect (Api)
    useEffect(() => {
      fetchList();
    }, []);

    ////////////////////////////////////////
    // ---- Modal Form Helpers ----
    // close
    const closeAll = () => {
      setShowCreate(false);
      setShowEdit(false);
      setShowView(false);
      setShowDelete(false);
    };

    // reset
    const resetForm = () => {
      setForm({ categoryName: '' });
      setFormError({});
    };

    // Aç/Kapat
    const openCreate = () => {
      closeAll();
      resetForm();
      setShowCreate(true);
    };
    const closeCreate = () => {
      setShowCreate(false);
      resetForm();
    };

    // Edit Aç/Kapa
    const openEdit = (row) => {
      closeAll();
      setSelected(row);
      setForm({ categoryName: row?.categoryName || '' });
      setFormError({});
      setShowEdit(true);
    };

    const closeEdit = () => {
      setShowEdit(false);
      setSelected(null);
      resetForm();
    };

    // View Aç/Kapa
    const openView = (row) => {
      closeAll();
      setSelected(row);
      setShowView(true);
    };

    const closeView = () => {
      setShowView(false);
      setSelected(null);
    };

    // Delete Aç/Kapa
    const openDelete = (row) => {
      closeAll();
      setSelected(row);
      setShowDelete(true);
    };

    const closeDelete = () => {
      setShowDelete(false);
      setSelected(null);
    };

    ////////////////////////////////////////
    // Pagination
    // ---- Search / Sort / Paginate ----
    // ---- Filter ----
    const filtered = useMemo(() => {
      const q = query.trim().toLowerCase();
      if (!q) return items;
      return items.filter((x) => {
        const id = (x.categoryId ?? x.id ?? '').toString();
        const name = (x.categoryName ?? '').toLowerCase();
        return id.includes(q) || name.includes(q);
      });
    }, [items, query]);

    // ---- Sort----
    const sorted = useMemo(() => {
      const arr = [...filtered];
      arr.sort((a, b) => {
        const va =
          sortKey === 'categoryName'
            ? (a.categoryName ?? '').toLowerCase()
            : sortKey === 'systemCreatedDate'
              ? new Date(a.systemCreatedDate || 0).getTime()
              : (a.categoryId ?? a.id ?? 0);

        const vb =
          sortKey === 'categoryName'
            ? (b.categoryName ?? '').toLowerCase()
            : sortKey === 'systemCreatedDate'
              ? new Date(b.systemCreatedDate || 0).getTime()
              : (b.categoryId ?? b.id ?? 0);

        const r = va < vb ? -1 : va > vb ? 1 : 0;
        return sortDir === 'asc' ? r : -r;
      });
      return arr;
    }, [filtered, sortKey, sortDir]);

    const total = sorted.length;
    const pageCount = Math.max(1, Math.ceil(total / pageSize));
    const currentPage = Math.min(page, pageCount);
    const paged = useMemo(() => {
      const start = (currentPage - 1) * pageSize;
      return sorted.slice(start, start + pageSize);
    }, [sorted, currentPage, pageSize]);

    ////////////////////////////////////////
    // Form onChange
    const onChange = (event) => {
      const { name, value } = event.target;
      setForm((temp) => ({ ...temp, [name]: value }));
      setFormError((temp) => ({ ...temp, [name]: undefined })); // Hata varsa temizle
    }; // end onChange

    // ------ CRUD ------
    // Create
    const submitCreate = async (event) => {
      event.preventDefault();
      const err = {};
      if (!form.categoryName?.trim()) {
        err.categoryName = 'Kategori adı boş olamaz';
        setformError(err);
      }

      if (Object.keys(err).length > 0) {
        return;
      }

      try {
        //const res = await axios.post(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.CREATE}`, form);
        const res = await axios.post(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.CREATE}`, {
          categoryName: form.categoryName.trim(),
        });

        showSuccess?.('Kategori başarıyla eklendi') ?? console.log('Kategori eklendi', res.data);
        closeCreate();
        fetchList();
      } catch (ex) {
        showError?.(ex?.response?.data?.message || 'Kategori eklenemedi') ?? console.error(ex);
        //setFormError((temp) => ({ ...temp, categoryName: ex?.response?.data?.message || 'Hata' }));
        setFormError(ex?.response?.data?.validationErrors || {});
      }
    };

    // Edit
    const submitEdit = async (event) => {
      event.preventDefault();
      const err = {};
      if (!form.categoryName?.trim()) {
        err.categoryName = 'Kategori adı boş olamaz';
        setformError(err);
      }

      if (Object.keys(err).length > 0) {
        return;
      }

      try {
        const id = selected?.categoryId ?? selected?.id;
        if (id == null) {
          throw new Error('Blog Category ID yok.');
        }

        //const res = await axios.post(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.CREATE}`, form);
        const res = await axios.put(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.UPDATE(id)}`, {
          categoryName: form.categoryName.trim(),
        });

        showSuccess?.('Kategori başarıyla güncellendi') ??
          console.log('Kategori güncellendi', res.data);
        closeEdit();
        fetchList();
      } catch (ex) {
        showError?.(ex?.response?.data?.message || 'Kategori güncellendi') ?? console.error(ex);
        //setFormError((temp) => ({ ...temp, categoryName: ex?.response?.data?.message || 'Hata' }));
        setFormError(ex?.response?.data?.validationErrors || {});
      }
    };

    // Delete
    const confirmDelete = async () => {
      try {
        const id = selected?.categoryId ?? selected?.id;
        if (id == null) {
          throw new Error('Blog Category ID yok.');
        }
        const res = await axios.delete(`${API_BASE}${ENDPOINTS.BLOG_CATEGORY.DELETE(id)}`);
        showSuccess?.('Kategori başarıyla silindi') ?? console.log('Kategori silindi', res.data);
        closeDelete();
        fetchList();
      } catch (ex) {
        showError?.(ex?.response?.data?.message || 'Kategori silinemedi') ?? console.error(ex);
      }
    };

    // ---- Render ----
    const SortBtn = ({ k, children }) => (
      <button
        type="button"
        className="btn btn-link p-0 ms-1"
        title="Sırala"
        onClick={() => {
          if (sortKey === k) setSortDir((d) => (d === 'asc' ? 'desc' : 'asc'));
          else {
            setSortKey(k);
            setSortDir('asc');
          }
        }}
      >
        {children} {sortKey === k ? (sortDir === 'asc' ? '▲' : '▼') : ''}
      </button>
    );

    ////////////////////////////////////////
    // Return
    return (
      <React.Fragment>
        <div className="container py-4">
          <div class="d-flex align-items-center justify-content-between mb-3">
            <h2 className="mb-0">Blog Kategoriler</h2>
            <div class="d-flex gap-2">
              <input
                type="text"
                className="form-control"
                style={{ minWidth: 220 }}
                placeholder="Kategori ara (ID/Ad)..."
                value={query}
                onChange={(e) => {
                  setQuery(e.target.value);
                  setPage(1); // Arama yapıldığında sayfayı 1'e sıfırla
                }}
              />
              <button
                className="btn btn-primary"
                onClick={() => {
                  openCreate();
                }}
              >
                Yeni Kategori Ekle
              </button>
            </div>
            {/* end filter */}
          </div>

          {/* Table */}
          <div className="table-responsive">
            <table className="table table-striped table-bordered align-middle">
              <thead>
                <tr>
                  <th style={{ minWidth: 90 }}>
                    ID <SortBtn k="categoryId" />
                  </th>
                  <th style={{ minWidth: 120 }}>
                    Kategori Adı <SortBtn k="categoryName" />
                  </th>
                  <th style={{ minWidth: 220 }}>
                    Oluşturulma Tarihi <SortBtn k="systemCreatedDate" />
                  </th>
                  <th style={{ minWidth: 160 }}>İşlemler </th>
                </tr>
              </thead>
              <tbody>
                {loading ? (
                  <tr>
                    <td colSpan={4} className="text-center py-1">
                      <span
                        className="spinner-border spinner-border-sm me-2  text-primary"
                        role="status"
                      />
                      Yükleniyor...
                    </td>
                  </tr>
                ) : paged.length === 0 ? (
                  <tr>
                    <td colSpan={4} className="text-center py-1 text-muted">
                      <span className="text-muted">Kayıt bulunamadı.</span>
                    </td>
                  </tr>
                ) : (
                  paged.map((row) => (
                    <tr key={row.categoryId ?? row.id}>
                      {/* ID */}
                      <td>{row.categoryId ?? row.id}</td>
                      {/* Category Name */}
                      <td>{row.categoryName}</td>
                      {/* Date */}
                      {/* <td>{new Date(row.systemCreatedDate).toLocaleDateString()}</td> */}
                      <td>{fmtDate(row.systemCreatedDate)}</td>

                      {/* İşlemler */}
                      <td>
                        <div className="btn-group btn-group-sm">
                          <button
                            className="btn btn-sm btn-outline-primary me-2"
                            title="Düzenle"
                            onClick={() => openEdit(row)}
                          >
                            {/* Düzenle */}
                            <i className="fas fa-edit" />
                          </button>

                          <button
                            className="btn btn-sm btn-outline-primary me-2"
                            title="Göster"
                            onClick={() => openView(row)}
                          >
                            {/* Göster */}
                            <i className="fas fa-eye" />
                          </button>

                          <button
                            className="btn btn-sm btn-outline-danger"
                            title="Sil"
                            onClick={() => openDelete(row)}
                          >
                            <i className="fas fa-trash" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
          {/* end Table */}

          {/* Pagination */}
          <div className="d-flex justify-content-between align-items-center mt-2">
            <div>
              Toplam <b>{total}</b> kayıt, Sayfa <b>{currentPage}</b> / <b>{pageCount}</b>
            </div>
            <div className="d-flex align-items-center gap-2">
              <select
                className="form-select"
                value={pageSize}
                onChange={(e) => {
                  setPageSize(parseInt(e.target.value || '10', 10));
                  setPage(1);
                }}
                style={{ width: 90 }}
              >
                {[5, 10, 20, 50, 100].map((n) => (
                  <option key={n} value={n}>
                    {n}/sayfa
                  </option>
                ))}
              </select>
              <div className="btn-group">
                <button
                  className="btn btn-outline-secondary"
                  disabled={currentPage <= 1}
                  onClick={() => setPage((p) => Math.max(1, p - 1))}
                >
                  ‹
                </button>
                <button
                  className="btn btn-outline-secondary"
                  disabled={currentPage >= pageCount}
                  onClick={() => setPage((p) => Math.min(pageCount, p + 1))}
                >
                  ›
                </button>
              </div>
            </div>
          </div>
          {/* end Pagination */}

          {/* CREATE MODAL */}
          {showCreate && (
            <div
              className="modal fade show d-block"
              tabIndex="-1"
              role="dialog"
              tabIndex={-1}
              aria-hidden="true"
              style={{ zIndex: 1050 }}
              onClick={closeCreate}
            >
              <div
                className="modal-dialog modal-dialog-centered"
                role="document"
                onClick={(e) => e.stopPropagation()} // Modal içi tıklamayı engelle
              >
                <div className="modal-content">
                  <form onSubmit={submitCreate}>
                    {/* HEADER */}
                    <div className="modal-header">
                      <h5 className="modal-title">Yeni Kategori Ekle</h5>
                      <button type="button" className="btn-close" onClick={closeCreate}></button>
                    </div>

                    {/* BODY */}
                    <div className="modal-body">
                      <div className="mb-3">
                        <label htmlFor="categoryName" className="form-label">
                          Kategori Adı
                        </label>
                        <input
                          type="text"
                          required
                          id="categoryName"
                          name="categoryName"
                          value={form.categoryName}
                          onChange={onChange}
                          className={`form-control ${formError.categoryName ? 'is-invalid' : ''}`}
                        />

                        {formError.categoryName && (
                          <div className="invalid-feedback">{formError.categoryName}</div>
                        )}
                      </div>
                    </div>

                    {/* FOOTER */}
                    <div className="modal-footer">
                      <button type="button" className="btn btn-secondary" onClick={closeCreate}>
                        İptal
                      </button>
                      <button type="submit" className="btn btn-primary">
                        Kaydet
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          )}

          {/* EDIT MODAL */}
          {showEdit && (
            <div
              className="modal fade show d-block"
              tabIndex="-1"
              role="dialog"
              tabIndex={-1}
              aria-hidden="true"
              style={{ zIndex: 1050 }}
              onClick={closeEdit}
            >
              <div
                className="modal-dialog modal-dialog-centered"
                role="document"
                onClick={(e) => e.stopPropagation()} // Modal içi tıklamayı engelle
              >
                <div className="modal-content">
                  <form onSubmit={submitEdit}>
                    {/* HEADER */}
                    <div className="modal-header">
                      <h5 className="modal-title">Kategori Düzenle</h5>
                      <button type="button" className="btn-close" onClick={closeEdit}></button>
                    </div>

                    {/* BODY */}
                    <div className="modal-body">
                      <div className="mb-3">
                        <label htmlFor="categoryName" className="form-label">
                          Kategori Adı
                        </label>
                        <input
                          type="text"
                          required
                          id="categoryName"
                          name="categoryName"
                          value={form.categoryName}
                          onChange={onChange}
                          className={`form-control ${formError.categoryName ? 'is-invalid' : ''}`}
                        />

                        {formError.categoryName && (
                          <div className="invalid-feedback">{formError.categoryName}</div>
                        )}
                      </div>
                    </div>

                    {/* FOOTER */}
                    <div className="modal-footer">
                      <button type="button" className="btn btn-secondary" onClick={closeEdit}>
                        İptal
                      </button>
                      <button type="submit" className="btn btn-primary">
                        Kaydet
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          )}

          {/* VIEW MODAL */}
          {showView && (
            <div
              className="modal fade show d-block"
              tabIndex="-1"
              role="dialog"
              tabIndex={-1}
              aria-hidden="true"
              style={{ zIndex: 1050 }}
              onClick={closeView}
            >
              <div
                className="modal-dialog modal-dialog-centered"
                role="document"
                onClick={(e) => e.stopPropagation()} // Modal içi tıklamayı engelle
              >
                <div className="modal-content">
                  {/* HEADER */}
                  <div className="modal-header">
                    <h5 className="modal-title">Kategori Detayı</h5>
                    <button type="button" className="btn-close" onClick={closeView}></button>
                  </div>

                  {/* BODY */}
                  <div className="modal-body">
                    <div className="mb-2">
                      <b>ID:</b> {selected?.categoryId ?? selected?.id}
                    </div>

                    <div className="mb-2">
                      <b>Kategori Adı:</b> {selected?.categoryName ?? selected?.name}
                    </div>

                    <div className="mb-2">
                      <b>Oluşturulma Tarihi:</b> {selected?.createdDate ?? selected?.date}
                    </div>
                  </div>
                  {/* FOOTER */}
                  <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" onClick={closeView}>
                      İptal
                    </button>
                  </div>
                </div>
              </div>
            </div>
          )}

          {/* DELETE MODAL */}
        </div>
        {/* end container */}
      </React.Fragment>
    );
  } // end BlogCategory
} // end GlobalBackdrop
// Export
export default BlogCategory()();
